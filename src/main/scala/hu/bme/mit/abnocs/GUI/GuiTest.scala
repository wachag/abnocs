package hu.bme.mit.abnocs.GUI

import akka.actor.{ActorSystem, Props}
import hu.bme.mit.abnocs.{AddNOCObject, Start, TestNOC}
import org.graphstream.graph.implementations.MultiGraph
import org.jfree.chart.{ChartFactory, ChartPanel}
import org.jfree.data.category.DefaultCategoryDataset
import org.jfree.chart.plot.PlotOrientation

import scala.util.Random

/*
object GuiTest extends SimpleSwingApplication {

  val ATTENTION = "Attention"
  val MEDITATION = "Meditation"

  val data = new DefaultCategoryDataset
  data.addValue(100.0, ATTENTION, 1)
  data.addValue(200.0, ATTENTION, 2)
  data.addValue(300.0, ATTENTION, 3)
  data.addValue(400.0, ATTENTION, 4)
  data.addValue(500.0, ATTENTION, 5)

  data.addValue(500.0, MEDITATION, 1)
  data.addValue(400.0, MEDITATION, 2)
  data.addValue(300.0, MEDITATION, 3)
  data.addValue(200.0, MEDITATION, 4)
  data.addValue(100.0, MEDITATION, 5)

  val chart = ChartFactory.createLineChart(
    "Brainwaves", "Time", "Value",
    data, PlotOrientation.VERTICAL,
    true, true, true)

  def top = new MainFrame {
    title = "Brainwave Plotter"
    peer.setContentPane(new ChartPanel(chart))
    peer.setLocationRelativeTo(null)
  }

}
*/

import java.awt.Dimension

import swing._
import swing.event._

object GuiTest extends SimpleSwingApplication {
  def top = new MainFrame {
    title = "NoC tester"
    val data = new DefaultCategoryDataset
    val g=new MultiGraph("Topology")
    val viewer = g.display(true)
    val view = viewer.addDefaultView(false)
    g.setStrict(false)
    g.setAutoCreate(true)

    contents = new GridPanel(2, 2) {
      hGap = 0
      vGap = 0
      val X = "X"
      val chart = ChartFactory.createLineChart(
        "NoC messages", "CPU#", "Messages received",
        data, PlotOrientation.VERTICAL,
        true, true, true)
      contents += Component.wrap(new ChartPanel(chart))
      contents += new Label {
        text = "0"
      }
/*      contents += new Button {
        text = "Press Me!"
      }*/
      contents += Component.wrap(view)
    }
    size = new Dimension(1024, 768)
    val system = ActorSystem("ABNOCS")
    val logger = system.actorOf(Props(new GActor(data,g)), name = "logger")
    println(logger.path)
    val NOC = system.actorOf(Props[TestNOC], name = "noc1")
    //NOC ! AddNOCObject(logger)
    NOC ! Start()
  }
}