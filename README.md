# Functional programming in Scala : a practical introduction

This is the training material for a two days training session intended for exprerienced programmers without
prior experience of functional programming or the Scala programming language.

The training covers the most common syntax and structures of the Scala programming langauge such as methods,
functions, Options, Lists and so on. It also covers some of the most important concepts of functional
programming such as referntial transparency, totality, algebraic data types ...

These concepts are applied to the realization of a complete and idiomatic Scala application leveraging some
of the most important libraries of the ecosytem: Cats Effect and fs2. The project is a Tic Tac Toe game that
can be played on the command-line, either with both opponents on the same machine, or remotely by connecting
to a TCP server.

---

### Using the material

Beside the `master` branch, which contains the slides, this project has two main branches:

- The `exercises` branch which contains lessons and small associated exercises, as well as the boilerplate
for the Tic Tac Toe Project. Exercises have unit tests that you can run to evaluate your solution.
- The `solutions` branch contains the solutions to the exercises and the complete application.

---

### Launching the projects


#### Launch the tests for the small exercises

```
sbt test
```

#### Launch the tests for the Tic Tac Toe logic

```
sbt ticTacToeCore/test
```

#### Launch the local version of the game

```
sbt ticTacToeCli/run
```

#### Launch the network version of the game

```
sbt ticTacToeNetworkServer/run
```

To connect to the server

```
nc localhost 4567
```

The game starts when two people are connected to the server

### License

This is licensed under a Creative Commons 4.0 BY-NC-SA (Attribution, no commercial use, and share-alike policy).

#### Attribution

This project must be attributed to Guillaume Bogard (<hey@guillaumebogard.dev>).
