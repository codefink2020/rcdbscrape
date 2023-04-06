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
      id = Math.random().toString,
      name = doc.select("#feature div h1").text(),
      park = doc.select("#feature > div > a:nth-of-type(1)").text(),
      city = doc.select("#feature > div > a:nth-of-type(2)").text(),
      province = doc.select("#feature > div > a:nth-of-type(3)").text(),
      country = doc.select("#feature > div > a:nth-of-type(4)").text(),
      status = status._1 ,
      opening = status._2,
      closing = status._3,
      classification = Some(assignClassification(doc.select("#feature > ul:nth-of-type(1) > li:nth-of-type(1) > a").text())),
      rctype = Some(assignRcType(doc.select("#feature > .ll:nth-of-type(1) > li:nth-of-type(2) > a").text())),
      scale = Some(assignScale(doc.select("#feature > .ll:nth-of-type(1) > li:nth-of-type(4) > a").text())),
      design = Some(assignDesign(doc.select("#feature > .ll:nth-of-type(1) > li:nth-of-type(3) > a").text())),
      speed = None,
      inversions = None,
      trackLayout = None,
      tracklength = None,
      trackheight = None,
      category = None,
      restraints = None,
      elements = None, //todo: Parse the element find its ID and put in the list

    )
    display(result)
    result

  }

  private def parseStatus(block: Elements): (String, Option[String], Option[String]) = {

    val pstatus = block.select("p:nth-of-type(1)> a:nth-of-type(1)").text()
    val status = assignState(pstatus)

    var stopped: Option[String] = None
    val started = Some(block.select("p time:nth-of-type(1)").attr("datetime"))
    println("started: " + started)
    if (block.select("p time:nth-of-type(2)").attr("datetime").isEmpty) {
      stopped = None
    } else {
      stopped = Some(block.select("p time:nth-of-type(2)").attr("datetime"))
    }
    println("stopped: " + stopped)
    (status, started, stopped)
  }
  private def assignState(str: String): String = {
    if(str.contains("Operating")) {
      "Operating"
    } else if(str.contains("In Business")) {
      "In Business"
    } else if(str.contains("In Production")) {
      "In Production"
    } else if(str.contains("Living")) {
      "Living"
    }else if(str.contains("SBNO")) {
      "standing but not operating"
    }else if(str.contains("In storage")) {
      "In storage"
    } else {
      "CheckSite"
    }
  }


  def assignClassification(str:String):String = {
    str
  }
  def assignRcType(str:String):String = {
    str
  }
  def assign(str:String):String = {
    str
  }
  def assignScale(str:String):String = {
    str
  }
  def assignDesign(str:String):String = {
    str
  }
  def assignTrackLayout(str:String):String = {
    str
  }
  def assignCategory(str:String):String = {
    str
  }
  def assignRestraints(str:String):String = {
    str
  }

  private def display(r: Rollercoaster):Unit = {
    println(s"name: ${r.name}")
    println(s"park: ${r.park}")
    println(s"province: ${r.province}")
    println(s"city: ${r.city}")
    println(s"country: ${r.country}")
    println(s"status: ${r.status}")
    println(s"started: ${r.opening}")
    println(s"stopped: ${r.closing}")
    println(s"classification: ${r.classification}")
    println(s"type: ${r.rctype}")
    println(s"scale: ${r.scale}")
    println(s"design: ${r.design}")
    println(s"tracklayout: ${r.trackLayout}")
    println(s"category: ${r.category}")
    println(s"restraints: ${r.restraints}")

  }


}
