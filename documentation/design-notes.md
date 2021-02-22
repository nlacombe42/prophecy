# Design notes

- find a way to make the language forward compatible
  - what feels the most natural to me is to use keywords to denote a new language feature
  - I don't want to programmer to prefix all their symbols (ex: `$var`, `%var`, etc)
  - but then how to make sure that the compiler can always tell
    when something is a keyword vs a user identifier?
  - ideas
    - when a parser rule effectively start with an identifier-like token then a space,
      the identifier-like token must be a keyword
      - otherwise either a non-alphanumeric and non-whitespace character is used (ex: `methodName(43)` has a `(`)
      - but then how to distinguish variable declarations?  
        you could not write `String a;` because `String` would be understood as a keyword
        - maybe all keywords can start with lowercase and all types with uppercase
