package parser.rcdb

import org.jsoup._
import model.Rollercoaster
import org.jsoup.nodes.Element
import org.jsoup.select.Elements

import scala.collection.mutable.ListBuffer

class RollercoasterParser {

  def ParseRegion(region: String): List[Rollercoaster] = {
    val domain: String = "https://rcdb.com"
    val url = s"$domain/r.htm?ol=$region&ot=2" //ot=2 means Rollercoaster
    val rollerCoasters = new ListBuffer[Rollercoaster]

    val doc = Jsoup.connect(url).get()
    val totalCoasters: Int = doc.select(".int").text().toInt
    val pages = Math.ceil(totalCoasters / 24).toInt // assumes 24 items per page
    for (page <- 1 to pages) {
      val pagedData = Jsoup.connect(url+s"&page=$page").get()
      val rows: Elements = pagedData.select(".stdtbl tbody tr")
      rows.forEach(row => {
        val link: String = row.select("td:nth-of-type(2) a").attr("href")

        val coaster = getDetails(s"$domain$link")
        rollerCoasters.addOne(coaster)
      })
      println(s" parsing page: $page of $pages ")
    }
    rollerCoasters.toList
  }



  def getDetails(link: String): Rollercoaster = {
    val doc = Jsoup.connect(link).get()
    val status = parseStatus(doc.select("#feature p"))
    val result: Rollercoaster = Rollercoaster(
      name = doc.select("#feature h1").text(),
      park = doc.select("#feature a:nth-of-type(1)").text(),
      city = doc.select("#feature a:nth-of-type(2)").text(),
      province = doc.select("#feature a:nth-of-type(3)").text(),
      country = doc.select("#feature a:nth-of-type(4)").text(),
      status = status._1 ,
      opening = status._2,
      closing = status._3
    )
    display(result)
    result

  }

  def parseStatus(block: Elements): (String, Option[String], Option[String]) = {

    val status = block.select("p:nth-of-type(1) a:nth-of-type(1)")
    println(status)
    var stopped: Option[String] = None
    val started = Some(block.select("p time:nth-of-type(1)").attr("datetime"))
    println("started: " + started)
    if (block.select("p time:nth-of-type(2)").attr("datetime").isEmpty) {
      stopped = None
    } else {
      stopped = Some(block.select("p time:nth-of-type(2)").attr("datetime"))
    }
    println("stopped: " + stopped)
    ("status", started, stopped)
  }
  def display(r: Rollercoaster):Unit = {
    println(s"name: ${r.name}")
    println(s"park: ${r.park}")
    println(s"province: ${r.province}")
    println(s"city: ${r.city}")
    println(s"country: ${r.country}")
  }


}
