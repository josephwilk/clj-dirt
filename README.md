# Clojure Dirt

A Clojure wrapper around the wonderful samples provided by DIRT (and used by Tidal).

* Dirt https://github.com/yaxu/Dirt
* Tidal https://github.com/yaxu/Tidal

## Usage

```clojure
(use 'overtone.live)
(use 'dirt.core)

(sample-player 
  (dirt (rand-nth [:808 :bass2 :bass1 :bass0 :bend :bev :birds3]) (rand-int 100)))
```

## License

Copyright © 2014 Joseph Wilk

Distributed under the Eclipse Public License either version 1.0 or (at
your option) any later version.
