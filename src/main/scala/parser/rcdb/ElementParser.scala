package parser.rcdb

import model.RcElement
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.openqa.selenium.json.Json

import scala.collection.mutable.ListBuffer

class ElementParser {

  def parseElements(): List[RcElement] = {
    val url: String = "https://rcdb.com/10183.htm"
    val linkBuffer = new ListBuffer[String]
    val elementBuffer = new ListBuffer[RcElement]
    linkBuffer.addOne(url); // add the element displayed on the original page
    val doc = Jsoup.connect(url).get()
    val rawLinks = doc.select(".sec > ul")
    rawLinks.forEach(x => {
      val anchor = x.select("a")
      anchor.forEach(a => linkBuffer.addOne(s"https://rcdb.com${a.attr("href")}"))
    })
    linkBuffer.foreach(link => elementBuffer.addOne(getElementDetails(link)))
    elementBuffer.toList

  }
  def getElementDetails(link: String): RcElement = {
    val doc = Jsoup.connect(link).get()
    val inversions = getInversions(doc)

    val result = RcElement(
      id = "???", name = doc.select("#feature div h1").text(),
      description = doc.select(".text >tbody > tr > td").text(),
      inversions = inversions,
      rollercoasters = None //todo: solve the relation problem//
    )

      result
  }
  def getInversions(doc: Document): String = {
    val x = doc.select(".stat-tbl")
    var inversions: String = "0"
    if (x.text().contains("Inversions")) {
      inversions = x.select(" tbody > tr:nth-of-type(1) > td").text()
    }
    inversions
  }

}
