import model.{Company, RcElement}
import parser.rcdb.{CompanyParser, ElementParser, RollercoasterParser}

object Parser extends App {
  val rcdbRCParser = new RollercoasterParser
  val rcdbElementParser = new ElementParser
  val rcdbCompanyParser = new CompanyParser
  print("Parsing the elements: ")
  //val rcElement: List[RcElement] = rcdbElementParser.parseElements()
  println("Done")
  print("Parsing the companies for north america: ")
  val companies: List[Company] = rcdbCompanyParser.parseCompanies("1")
  println("Done")

  //val rcdbNACoasters = rcdbRCParser.ParseRegion("1")

  //when working set getDtails to private and delete this line.
  //val rc = rcdbRCParser.getDetails("https://rcdb.com/786.htm")
  //write to DB



}

