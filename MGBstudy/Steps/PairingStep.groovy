pairingStep = stepFactory.createStep()

pairingStep.run = {
  println "pairingStep.run: ${curRound}"
  curStep = "pairing"
  
  // assign conditions and set colors
  rmember = 0
  bmember = 0
  r1idx = 0
  r2idx = 0
  r3idx = 0
  r1score = 2000
  r2score = 2000
  r3score = 2000
  rtotal = 6000
  b1idx = 0
  b2idx = 0
  b3idx = 0
  b1score = 500
  b2score = 500
  b3score = 500
  btotal = 1500
  
  def condition = 0
  
    g.V.filter{ it.active }.shuffle.each { v ->
      v.condition = condition      
      if (condition == 0) {
        condition = 1
        v.col = "r"
        v.truescore = 2000
        if (rmember == 0) {
          v.r1 = true
          r1idx = v.score
          rmember += 1
        } else if (rmember == 1) {
          v.r2 = true
          r2idx = v.score
          rmember += 1
        } else {
          v.r3 = true
          r3idx = v.score
        }
        
      } else {
        condition = 0
        v.col = "b"
        v.truescore = 500
        if (bmember == 0) {
          v.b1 = true
          b1idx = v.score
          bmember += 1
        } else if (bmember == 1) {
          v.b2 = true
          b2idx = v.score
          bmember += 1
        } else {
          v.b3 = true
          b3idx = v.score
        }
      }
    }
    
    g.V.filter{it.active}.each { v ->
      if (v.col == "r") {
        v.text = c.get("RedGroup",v.truescore,r1idx,r2idx,r3idx,r1score,r2score,r3score,rtotal,b1idx,b2idx,b3idx,b1score,b2score,b3score,btotal) + "<p><strong>Click 'Begin' to join the game.</strong></p>"
        a.add(v, [name: "Begin", result: {
          v.text = c.get("PleaseWait") + "<p><strong>Thank you, the game will begin in a moment.</strong></p>"
        }])
      } else {
        v.text = c.get("BlueGroup",v.truescore,r1idx,r2idx,r3idx,r1score,r2score,r3score,rtotal,b1idx,b2idx,b3idx,b1score,b2score,b3score,btotal) + "<p><strong>Click 'Begin' to join the game.</strong></p>"
        a.add(v, [name: "Begin", result: {
          v.text = c.get("PleaseWait") + "<p><strong>Thank you, the game will begin in a moment.</strong></p>"
        }])
      }
    }    
  
  
  def p1 = null
  def p2 = null
  def p3 = null
  g.V.filter{ it.active }.shuffle.each { v-> 
    if (p1 == null) {
      v.p1 = true
      p1 = v
    } else {
      if (p2 == null) {
        v.p2 = true
        p2 = v
        g.addEdge(p1, p2, "connected")
      } else {
        v.p3 = true
        p3 = v
        g.addEdge(p1, p3, "connected")
        g.addEdge(p2, p3, "connected")
        p1 = null
        p2 = null
        p3 = null
      } 
    }
  }
  if (p1 != null) {
    println("unpaired p1")
    p1.dropped = true
  }
  if (p2 != null) {
    println("unpaired p2")
    p2.dropped = true
  }
  
}

pairingStep.done = {
  println "pairingStep.done: ${curRound}"
  proposalStep.start()
}
