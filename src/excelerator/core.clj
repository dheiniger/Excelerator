(ns excelerator.core
  (:require [clojure.string :as str])
  (:use [seesaw.core]
        [seesaw.mig])

  (:gen-class :main true))

(def user-input-text-area (text :id :input
                                :multi-line? true
                                :editable? true
                                :text ""))

(def output-text-area (text :multi-line? true
                            :editable? false
                            :text ""))

(defn cell+
  [s]
  (Double/parseDouble
    (format "%.2f"
            (apply +
                   (map #(if (str/blank? %) 0.0
                                            (Double/parseDouble %))
                        (map str/trim (str/split-lines (str/replace s "\"" ""))))))))

(defn swap-output-text []
  (let [i (value (select user-input-text-area [:#input]))]
    (prn i)
    (try
      (text! output-text-area (cell+ i))
      (catch Exception _
        (text! output-text-area "INVALID VALUE")))))

(def calculate-button (button :text "Calculate"
                              :font "ARIAL-BOLD-14"
                              :border -1
                              :background "#c8dce3"
                              :mnemonic \newline
                              :listen [:action (fn [_] (swap-output-text))]))


(defn -main []
  (-> (frame :title "Excelerator"
             :width 600
             :height 800
             :on-close :dispose
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



