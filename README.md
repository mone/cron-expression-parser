# Cron Expression Parser

## Project structure

Project is built with [mill](http://www.lihaoyi.com/post/MillBetterScalaBuilds.html), and
as such it adopts mill's project structure: the application is split into two modules

* core: contains the model and the parser
* cli: contains a cli interface for the parser

[Install mill](http://www.lihaoyi.com/mill/index.html#installation)

### Import

To prepare the project for intellij, run `mill mill.scalalib.GenIdeaModule/idea`
in the project folder (YMMV)

## Test

`mill all core.test cli.test`

or, to keep testing as the code changes,

`mill --watch all core.test cli.test`

### TODO

Need to find out how to integrate `scoverage` with `mill`

## Build

`mill cli.assembly`

## Run

`mill cli.run "* * * * * /foo/bar"`

or, after assembly:

`java -cp out/cli/assembly/dest/out.jar cronexpressionparser.cli.CronExpressionParserCli "* * * * * /foo/bar"`

### Note
When using * as one of the tokens, don't forget to escape it

eg.:
- this is ok (has no *): `mill cli.run */1 */1 */1 */1 */1 /foo/bar`
- this might fail: `mill cli.run * * * * * /foo/bar`
- this is ok again: `mill cli.run "* * * * * /foo/bar"`
- this works too: `mill cli.run \* \* \* \* \* /foo/bar`

Wondering what's happening? Try `echo *`