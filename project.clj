(defproject archy "0.1.0"
  :description "A simple setup script builder for Arch Linux"
  :url "https://github.com/vstan02/archy"
  :license {:name "GPL-3.0"}
  :dependencies [[org.clojure/clojure "1.11.1"]
                 [cheshire "5.12.0"]]
  :main archy.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all
                       :jvm-opts ["-Dclojure.compiler.direct-linking=true"]}})
