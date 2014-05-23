(ns dirt.core
  "Easy access to DIRT's samples: https://github.com/yaxu/Dirt.
   Clone the repo and port `dirt-home` to it."
  (:use [overtone.live] [clojure.java.io] [overtone.helpers.file])
  (:import java.io.File)
  (:import java.io.FileNotFoundException))

(defonce dirt-home (resolve-tilde-path "~/Workspace/music/Dirt/samples/"))
(defonce samples-cache (atom {}))

(defn- walk [^File dir]
  (let [children (.listFiles dir)
        subdirs (filter #(.isDirectory %) children)
        files (filter #(.isFile %) children)]
    (concat files (mapcat walk subdirs))))

(defn dirt
  "Fetches and caches locally dirt samples. All dirt samples are refered to by a containing folder `sample-name` and an int `n` which specifies which file to play. Only wav + aiff are supported by Overtone so ignore anything else.
   Example:
    (sample-player (dirt :amp 0))"
  ([sample-name] (dirt sample-name 1))
  ([sample-name n]
     (if-let [sample (@samples-cache (str (name sample-name) ":" n))]
       sample
       (let [sample-name (name sample-name)
             samples (->> (walk (file (str dirt-home sample-name "/")))
                          (filter #(re-find #"(?i)wav|aiff" (or (file-extension %1) ""))))
             n (if (>= n (count samples)) 0 n)
             sample-file (nth samples n)]
         (when sample-file
           (swap! samples-cache assoc (str sample-name ":" n) (load-sample sample-file))
           (@samples-cache (str sample-name ":" n)))))))
