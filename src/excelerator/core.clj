(ns excelerator.core
  (:require [clojure.string :as str])
  (:use [seesaw.core]
        [seesaw.mig])
  (:gen-class :main true))

(def background-color "#c8dce3")

(defn format-string
  [s]
  (str/split s #"\n"))

(defn format-strings
  [s]
  (if (not (str/includes? s "\""))
    (format-string s)
    (map #(str/split % #"\s+")
         (map #(str/replace % "\n" "")
              (remove str/blank?
                      (str/split s #"\""))))))

(defn total
  "converts a collection of strings and turns each value into double"
  [coll]
  (cond
    (coll? coll) (format "%.2f" (apply + (map #(Double/parseDouble %) coll)))
    (str/blank? coll) ""
    :else (format "%.2f" (Double/parseDouble coll))))

(defn cell+
  [s]
  (str/join "\n" (map total (format-strings s))))

;;USER INTERFACE-------------------------
(def user-input-text-area (text :id :input
                                :multi-line? true
                                :editable? true
                                :text ""
                                :background background-color
                                :listen [:key-typed (fn [_] (config! _ :background "#ff9375"))]))

(def output-text-area (text :multi-line? true
                            :editable? false
                            :background background-color
                            :text ""))

(defn swap-input-text-handler []
  (let [i (select user-input-text-area [:#input])]
    (config! i :background background-color)))

(defn swap-output-text-handler []
  (let [i (value (select user-input-text-area [:#input]))]
    (prn i)
    (try
      (text! output-text-area (cell+ i))
      (catch Exception _
        (text! output-text-area "INVALID VALUE")))))

(def calculate-button (button :id :calculate-button
                              :text "Calculate"
                              :font "ARIAL-BOLD-14"
                              :border -1
                              :background background-color
                              :mnemonic \newline
                              :listen [:action (fn [_] (swap-input-text-handler))
                                       :action (fn [_] (swap-output-text-handler))]))

(def help-action (menu-item :text "About" :listen [:action (fn [_](alert "Version: 1.0\nAuthor: Daniel Heiniger"))]))

(defn -main []
  (-> (frame :title "Excelerator"
             :width 600
             :height 800
             :on-close :dispose
             :menubar (menubar :items
                               [(menu :text "Help" :items [help-action])])
             :content (grid-panel
                        :columns 3
                        :items [(border-panel :north (flow-panel :vgap 20)
                                              :center (scrollable user-input-text-area)
                                              :south (flow-panel :vgap 20)
                                              :west (flow-panel :hgap 10))
                                (border-panel :north (flow-panel :vgap 180)
                                              :east (flow-panel :hgap 20)
                                              :center calculate-button
                                              :south (flow-panel :vgap 180)
                                              :west (flow-panel :hgap 20))
                                (border-panel :north (flow-panel :vgap 20)
                                              :center (scrollable output-text-area)
                                              :south (flow-panel :vgap 20)
                                              :east (flow-panel :hgap 10))]))
      show!))





