(ns simple-svg
  (:require [clojure.string :refer [join]]))

(def header-string "<?xml version=\"1.0\" encoding=\"utf-8\" ?><svg baseProfile=\"full\" height=\"100%%\" version=\"1.1\" viewBox=\"%f,%f,%f,%f\" width=\"100%%\" xmlns=\"http://www.w3.org/2000/svg\" xmlns:ev=\"http://www.w3.org/2001/xml-events\" xmlns:xlink=\"http://www.w3.org/1999/xlink\"><defs />")

(defn document
  "Adds header and preamble. Supply arguments to viewBox parameter"
  [x y w h body]
  (join [(format header-string x y w h) body "</svg>"]))

(defn polyline
  "Returns a string representing the polyline in svg-ese"
  [points]
  (join ["<polyline fill=\"none\" points=\""
         (join " " (map (fn [pt] (join "," [(:x pt)  (:y pt)])) points))
         "\" "
         "stroke=\"black\" stroke-width=\"1.0\" />"]))

(defn circle
  "Returns a string representing a circle in svg-ese"
  [circle]
  (str "<circle cx=\"" (float (:x circle))
       "\" cy=\"" (float (:y circle))
       "\" r=\"" (float (:r circle))
       "\" fill=\"" (cond (not (contains? :fill circle)) "none"
                          (= 'hsl (type (:fill circle))) (str "hsl("
                                                               (float (get-in circle [:fill :h])) ","
                                                               (float (get-in circle [:fill :s])) "%,"
                                                               (float (get-in circle [:fill :l])) "%)")
                          (= 'rgb (type (:fill circle))) (str "rgb("
                                                               (float (get-in circle [:fill :r])) ","
                                                               (float (get-in circle [:fill :g])) ","
                                                               (float (get-in circle [:fill :b])) ")"))
       "\" stroke=\"black\" stroke-width=\"1\"/>" ))

