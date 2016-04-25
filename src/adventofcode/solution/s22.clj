(ns adventofcode.solution.s22)

(defn handle-turns [player spell-name]
  (let [turns-left (get-in @player [:spells spell-name :turns-left])]
    (if (= turns-left 1)
      (swap! player update :spells dissoc spell-name)
      (swap! player update-in [:spells spell-name :turns-left] dec))
    (dec turns-left)))

(def spells
  {:magic-missle {:cost           53
                  :instant-effect (fn [_ boss print-hist]
                                    (print-hist "Player casts Magic Missile, dealing 4 damage.")
                                    (swap! boss update :hp - 4))}

   :drain        {:cost           73
                  :instant-effect (fn [player boss print-hist]
                                    (print-hist "Player casts Drain, dealing 2 damage, and healing 2 hit points.")
                                    (swap! boss update :hp - 2)
                                    (swap! player update :hp + 2))}

   :shield       {:cost   113
                  :effect {:turns     6
                           :effect-fn (fn [player _ print-hist]
                                        (let [turn (handle-turns player :shield)]
                                          (print-hist "Shield increases armor by 7; its timer is now " turn ".")
                                          (case turn
                                            5 (swap! player update :armor + 7)
                                            0 (swap! player update :armor - 7)
                                            :default)))}}

   :poison       {:cost   173
                  :effect {:turns     6
                           :effect-fn (fn [player boss print-hist]
                                        (let [turn (handle-turns player :poison)]
                                          (print-hist "Poison deals 3 damage; its timer is now " turn ".")
                                          (swap! boss update :hp - 3)))}}
   :recharge     {:cost   229
                  :effect {:turns     5
                           :effect-fn (fn [player _ print-hist]
                                        (let [turn (handle-turns player :recharge)]
                                          (print-hist "Recharge provides 101 mana; its timer is now " turn ".")
                                          (swap! player update :mana + 101)))}}})

(def the-boss
  {:name   "Boss"
   :hp     58
   :damage 9})

(def the-player
  {:name  "Player"
   :hp    50
   :mana  500
   :armor 0})

(defn apply-all-running-effects! [p boss print-hist]
  (doseq [[_ {:keys [effect-fn]}] (:spells @p)]
    (effect-fn p boss print-hist)))

(defn there-is-a-winnder? [player boss]
  (if (<= (:hp @player) 0)
    :boss
    (if (<= (:hp @boss) 0)
      :player)))

(defn switch-turn [turn]
  (if (= turn :player)
    :boss
    :player))

(defn boss-move [boss player print-hist]
  (let [boss-damage (:damage @boss)
        player-armor (:armor @player)]
    (print-hist "Boss attacks for " boss-damage " damage!")
    (swap! player update :hp - (max (- boss-damage player-armor) 1))))

(defn player-move [player boss mana-spend print-hist]
  (let [mana-left (:mana @player)
        all-spells-player-can-afford (keys (into {} (filter (fn [[_ spell]] (<= (:cost spell) mana-left)) spells)))]

    (if (empty? all-spells-player-can-afford)
      (do
        (print-hist "cant afford anything, Player dies")
        (swap! player assoc :hp 0))
      (let [still-running-spells (into #{} (keys (:spells @player)))
            all-spells-p-can-cast (filter (fn [s] (nil? (get still-running-spells s))) all-spells-player-can-afford)
            spell-name (nth all-spells-p-can-cast (rand-int (count all-spells-p-can-cast)))
            {:keys [cost effect instant-effect]} (get spells spell-name)]
        (swap! player update :mana - cost)
        (swap! mana-spend + cost)
        (if instant-effect
          (instant-effect player boss print-hist)
          (let [{:keys [turns effect-fn]} effect]
            (print-hist "Player casts " (name spell-name))
            (swap! player assoc-in [:spells spell-name] {:turns-left turns
                                                         :effect-fn  effect-fn})))))))

(defn run-the-game [isb?]
  (let [mana-spend (atom 0)
        history (atom "")
        print-hist (fn [& args] (swap! history str (apply str args) "\n"))
        player (atom the-player)
        boss (atom the-boss)]
    (loop [iteration 1
           turn :player]

      (when (and (= turn :player) isb?)
        (swap! player update :hp dec))

      (if-let [winner (there-is-a-winnder? player boss)]
        {:winner     winner
         :mana-spend @mana-spend
         :history    @history
         :iteration  iteration}
        (do
          (print-hist)
          (print-hist "-- " (name turn) " turn --")
          (apply-all-running-effects! player boss print-hist)
          (print-hist "- Player has " (:hp @player) " hit points, " (:armor @player) " armor, " (:mana @player) " mana")
          (print-hist "- Boss has " (:hp @boss) " hit points")

          (if-let [winner (there-is-a-winnder? player boss)]
            {:winner     winner
             :mana-spend @mana-spend
             :history    @history
             :iteration  iteration}
            (do
              (case turn
                :boss (boss-move boss player print-hist)
                :player (player-move player boss mana-spend print-hist))
              (recur (inc iteration) (switch-turn turn)))))))))


(defn the-secret-of-mana [& {:keys [isb?] :or {:isb? false}}]
  (loop [least-mana-spend 99999999
         game 1
         the-history ""
         wins {:player 0
               :boss   0}]
    (if (> game 100000)
      (do
        (println least-mana-spend)
        (println wins))
      (let [{:keys [winner mana-spend history]} (run-the-game isb?)]
        (if (and
              (= winner :player)
              (< mana-spend least-mana-spend))
          (recur mana-spend (inc game) history (update wins winner inc))
          (recur least-mana-spend (inc game) the-history (update wins winner inc)))))))

(defn starta []
  (println "Starting solution nr. 22a")
  (the-secret-of-mana))

(defn startb []
  (println "Starting solution nr. 22b")
  (the-secret-of-mana :isb? true))
