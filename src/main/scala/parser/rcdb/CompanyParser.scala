package parser.rcdb

import model.Company
import org.jsoup.Jsoup
import org.jsoup.select.Elements

import scala.collection.mutable.ListBuffer

class CompanyParser {

  def parseCompanies(region: String ):List[Company] = {
    val domain: String = "https://rcdb.com"
    val url = s"$domain/r.htm?ol=$region&ot=12" //ot=12 means company

    val companies = new ListBuffer[Company]

    val doc = Jsoup.connect(url).get()
    val totalCompanies: Int = doc.select(".int").text().toInt
    val pages = Math.ceil(totalCompanies / 24).toInt

    for(page <- 1 to pages) {
      val pagedData = Jsoup.connect(url+s"&page=$page").get()
      val rows: Elements = pagedData.select(".stdtbl tbody tr")
      rows.forEach(row => {
        val link: String = row.select("td:nth-of-type(2) a")attr("href")
        val company = getDetails(s"$domain$link")
      })
    }
    companies.toList
  }

  def getDetails(url: String): Company = {
    val doc = Jsoup.connect(url).get()
    val result: Company = Company(
      name = doc.select("#feature div h1").text(),
      address = doc.select("#feature > div").first().child(1).text(),
      city = doc.select("#feature > div").first().child(2).text(),
      province = doc.select("#feature > div").first().child(3).text(),
      country = doc.select("#feature > div").first().child(4).text(),
      owner = "???", status = "???", opened = "???", closed = Some("???")
    )
    display(result)
    result
  }

  private def display(c: Company): Unit = {
    println(s"name: ${c.name}")
    println(s"Address: ${c.address}")
    println(s"province: ${c.province}")
    println(s"city: ${c.city}")
    println(s"country: ${c.country}")
  }

}
