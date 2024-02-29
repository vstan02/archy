(ns archy.code
  (:gen-class)
  (:require [clojure.string :as string]))

(defn words [& args]
  (string/join " " args))

(defn lines [& args]
  (string/join "\n" args))

(defn sections [& args]
  (string/join "\n\n" args))
