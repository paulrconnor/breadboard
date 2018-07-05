onJoinStep = stepFactory.createNoUserActionStep()

onJoinStep.run = { playerId->
  def player = g.getVertex(playerId)
  if(n<4){  
    player.active = true // Active players have not been dropped
    player.p1 = false
    player.p2 = false
    player.p3 = false
    player.allocation = -1
    player.score = n
    n = n+1
    player.truescore = 1000
    player.col = "g"

    player.text = c.get("Welcome",player.score,player.truescore)
    a.add(player, [name: "Next", result: {
      player.text = c.get("Tutorial1")
    }])
    a.add(player, [name: "Next", result: {
      player.text = c.get("Tutorial2", sum)
    }])
    a.add(player, [name: "Next", result: {
      player.text = c.get("Tutorial3")
    }])
    a.add(player, [name: "Next", result: {
      player.text = c.get("PleaseWait")
    }])
  } else {
    a.addEvent("DropPlayer", ["pid": (player.id)])
    player.text = c.get("Dropped")
    // drop the player from the graph
    player.active = false
    player.dropped = true
    a.remove(player)
    a.remove(player.id)
    a.remove(g.getVertex(player.id))
    player.getEdges(Direction.BOTH).each { g.removeEdge(it) }
  }
}
onJoinStep.done = {
}