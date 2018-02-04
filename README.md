# EBNF-AST Generator

*Bnf-Ast Generator* is a parser generator framework for parsing EBNF syntaxes with Java code. Unlike other EBNF-Parser must write `config file` (like yacc, flex) , with BNF-AST Generator you can define EBNF Parser in Java Code.

## Install

* with maven:
  ``` xml
  <!-- Step 1. Add the JitPack repository to your build file -->
  <repositories>
  	<repository>
  		 <id>jitpack.io</id>
  		 <url>https://jitpack.io</url>
  	</repository>
  </repositories>
  <!-- Step 2. Add the dependency -->
  <dependency>
  	 <groupId>com.github.lfkdsk</groupId>
  	 <artifactId>bnf-ast</artifactId>
  	 <version>v3.10</version>
  </dependency>
  ```

## Introduce

As the library's name "EBNF-AST Generator", Developers use combinators defined in [BnfComs](src/main/java/bnfgenast/bnf/BnfCom.java) to define DSL syntaxes in Java Code. Library will auto generate parsers with this syntaxes.

Here is an example for you to for parsing Java `switch` syntax.

``` java
// define number, string, bool ... primary types, expression, body
// case: { body }
BnfCom caseExpr = rule(Case.class).token("case").sep(":").then(body);

// default: { body }
BnfCom defaultExpr = rule(Default.class).token("default").sep(":").then(body);

// switch ( expr ) {
//    case* | default
// }
BnfCom switchExpr = rule(Switch.class).token("switch").sep("(").then(expr).sep(")")
  .sep("{")
  .or(
	wrapper().repeat(caseExpr),
  	defaultExpr
).sep("}");
```

Now you can create a `Lisp` Parser quickly with EBNF-AST Generator: 

``` java
BnfCom number = rule().number(NumberLiteral.class);
BnfCom id = rule().identifier(IDLiteral.class, reservedToken);
BnfCom string = rule().string(StringLiteral.class);
BnfCom bool = rule().bool(BoolLiteral.class);

// primary ::= number | id | string | bool
BnfCom primary = wrapper().or(number, id, string, bool);
BnfCom expr0 = rule();
// quote   ::= `expr
BnfCom quote = rule().token("`").then(expr0);
// expr    ::= primary | quote | "(" expr* ")"
BnfCom expr = expr0.reset().or(primary, quote, rule().sep("(").repeat(expr0).sep(")"));
// problem ::= "(" expr* ")"
BnfCom problem = rule().sep("(").repeat(expr).sep(")");
```

you can get more information from Lisp test: [Lisp Syntaxes Test](src/test/java/lisp/LispTest.java).

## Support Combinators

*EBNF-AST Generator* support three types basic Combinators: *leaf*, *list*, *capture* .

### Leaf Combinator

The leaf combinator corresponds to a leaf node in the AST.

| Combinator Name            | Description                              | Usage                                    |
| -------------------------- | ---------------------------------------- | ---------------------------------------- |
| token(*string\**)          | `token` is used to define a reserved token as an `AstLeaf` in Ast. But token string must be marked as `reversed token` that you can use in token operator. | [Leaf Usage](src/test/java/bnfgenast/bnf/leaf/LeafTest.java) |
| sep(*string\**)            | `sep` combinator is used to skip an token, this token won't be used in AST. Just like usually function define `lfkdsk()` , token "(", ")" is useless should be skip. | [Skip Usage](src/test/java/bnfgenast/bnf/leaf/SkipTest.java) |
| maybe(*string*)            | `maybe` contains this string. token won't be used in AST. | [Maybe Usage](src/test/java/bnfgenast/bnf/leaf/SkipORTest.java) |
| number(*class*)            | `number`  represents number‘s token，EBNF-AST won't provide a default tokenizer, you should splite number token by yourselves. | [Token Test](src/test/java/bnfgenast/bnf/token/TokenTest.java) |
| string(*class*)            | `string` represents string's token.      | [Token Test](src/test/java/bnfgenast/bnf/token/TokenTest.java) |
| bool(*class*)              | `bool` represents boolean's token.       | [Token Test](src/test/java/bnfgenast/bnf/token/TokenTest.java) |
| id(*class*)                | `id` represents id's token.              | [Token Test](src/test/java/bnfgenast/bnf/token/TokenTest.java) |
| literal(*class*, *string*) | `literal` expects an literal string in AST. | [Literal Test](src/test/java/bnfgenast/bnf/token/LiteralTest.java) |

### List Combinator

The list combinator corresponds to the non-leaf node in the AST.

| Combinator Name | Description                              | Usage                                    |
| --------------- | ---------------------------------------- | ---------------------------------------- |
| ast / then      | `ast/ then` operator expects a subBnf parser in this parser. | [Leaf Usage](src/test/java/bnfgenast/bnf/leaf/LeafTest.java) |
| or              | `or` combinator expects one of several possible Parser supporters. | [Or Tree](src/test/java/bnfgenast/bnf/tree/OrTreeTest.java) |
| maybe           | `maybe` combinator expects a Parser option that may exist. Even `maybe` don't exist, maybe will generate an empty List in AST. | [Maybe Tree](src/test/java/bnfgenast/bnf/tree/MaybeTreeTest.java) |
| option          | `option` combinator expects a Parser option that may exist. Unlike `maybe`, if the `option` is not triggered, there is no empty node in the AST | [Option Tree](src/test/java/bnfgenast/bnf/tree/OptionTreeTest.java) |
| repeat          | `repeat` combinator represents a Parser that matches repeatedly. | [Repeat Tree](src/test/java/bnfgenast/bnf/tree/RepeatTest.java) |
|                 |                                          |                                          |
|                 |                                          |                                          |
|                 |                                          |                                          |




## License

[MIT License](LICENSE)