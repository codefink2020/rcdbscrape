import parser.rcdb.{ElementParser, RollercoasterParser}

object Parser extends App {
  val rcdbRCParser = new RollercoasterParser
  val rcdbElementParser = new ElementParser

  //val rcdbNACoasters = rcdbRCParser.ParseRegion("1")

  //when working set getDtails to private and delete this line.
  //val rc = rcdbRCParser.getDetails("https://rcdb.com/786.htm")
  //write to DB
  rcdbElementParser.parseElements()


}

