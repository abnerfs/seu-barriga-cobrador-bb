(ns app
  (:require [clojure.string :as string]
            [clojure.edn :as edn]
            [pod.tzzh.mail :as m]))

(def template  (slurp "resources/template.html"))
(def env (-> (slurp "resources/env.edn")
             (edn/read-string)))

(def share-value
  (let [total-shares (->> (:payers env)
                          (map :shares)
                          (reduce + 0))]
    (bigdec (/ (:total-value env) total-shares))))


(defn format-money
  [amount]
  (string/replace (format "%.2f" amount) #"\.", ","))

(defn e-mail-template
  [template {:keys [name shares]}]
  (-> template
      (string/replace #"\{\{name\}\}" name)
      (string/replace #"\{\{amount\}\}" (format-money (* shares share-value)))
      (string/replace #"\{\{pix-key\}\}" (:pix-key env))))

(defn ->email
  [{:keys [email] :as payer}]
  (-> (:smtp env)
      (select-keys [:host :port :username :password :subject :from])
      (update :subject e-mail-template payer)
      (assoc :to [email]
             :html (e-mail-template template payer))))

(defn send-emails []
  (->> (:payers env)
       (map ->email)
       (run! m/send-mail)))
