(ns ^{:doc "The graph index API."
      :author "Paula Gearon"}
    asami.graph
  (:require #?(:clj  [schema.core :as s]
               :cljs [schema.core :as s :include-macros true])))


(defprotocol Graph
  (new-graph [this] "Creates an empty graph of the same type")
  (graph-add [this subj pred obj] "Adds triples to the graph")
  (graph-delete [this subj pred obj] "Removes triples from the graph")
  (graph-diff [this other] "Returns all subjects that have changed in this graph, compared to other")
  (resolve-triple [this subj pred obj] "Resolves patterns from the graph, and returns unbound columns only")
  (count-triple [this subj pred obj] "Resolves patterns from the graph, and returns the size of the resolution"))

(def GraphType (s/pred (partial satisfies? Graph)))

(defn resolve-pattern
  "Convenience function to extract elements out of a pattern to query for it"
  [graph [s p o :as pattern]]
  (resolve-triple graph s p o))

(defn count-pattern
  "Convenience function to extract elements out of a pattern to count the resolution"
  [graph [s p o :as pattern]]
  (count-triple graph s p o))

