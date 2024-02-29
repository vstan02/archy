(ns archy.core
  (:gen-class)
  (:require [clojure.java.io :as io]
            [cheshire.core :as json]
            [archy.script :as script]))

(defn ^:private app [src dest]
  (let [stream (io/reader src)
        cfg (json/parse-stream stream true)]
    (spit dest (script/make cfg))))

(defn ^:private error [message]
  (println "[ERROR]: " message ".")
  (System/exit 0))

(defn -main [& args]
  (try
    (if (< (count args) 2)
     (error "Expected 'src' and 'dest' paths to be provided")
     (app (first args) (second args)))
    (catch Exception err
      (error (.getMessage err)))))
