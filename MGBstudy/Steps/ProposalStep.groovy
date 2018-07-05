proposalStep = stepFactory.createStep()

proposalStep.run = {
  println "proposalStep.run: ${curRound}"
  curStep = "proposal"
  
  g.V.filter{it.active && it.p3}.each { p3->
    
    p3.neighbors.each { n ->
      if(n.p1){ 
        p1idx = n.score
        p1col = n.col
      } else {
        p2idx = n.score
        p2col = n.col
      }
    }
    if (p1col == "r" && p2col == "b"){
      p3.text = c.get("AllocationRB",p1idx,p2idx)
    }
    if (p1col == "b" && p2col == "r"){
      p3.text = c.get("AllocationBR",p1idx,p2idx)
    }
    if (p1col == "r" && p2col == "r"){
      p3.text = c.get("AllocationRR",p1idx,p2idx)
    }
    if (p1col == "b" && p2col == "b"){
      p3.text = c.get("AllocationBB",p1idx,p2idx)
    }
    a.add(p3,
            [name: "A",
             result:{
               p3.text = c.get("WaitAllocation")
               p3.neighbors.each{ n ->
                 if(n.score == p1idx){
                   n.truescore += 100
                 } else {
                   n.truescore += 200
                 }}}],
            [name: "B",
             result:{
               p3.text = c.get("WaitAllocation")
               p3.neighbors.each{ n ->
                 if(n.score == p1idx){
                   n.truescore += 180
                 } else {
                   n.truescore += 220
                 }}}])
  }
  
  g.V.filter{it.active && it.p2}.each { p2->
    
    p2.neighbors.each { n ->
      if(n.p1){ 
        p1idx = n.score
        p1col = n.col
      } else {
        p3idx = n.score
        p3col = n.col
      }
    }
    if (p1col == "r" && p3col == "b"){
      p2.text = c.get("AllocationRB",p1idx,p3idx)
    }
    if (p1col == "b" && p3col == "r"){
      p2.text = c.get("AllocationBR",p1idx,p3idx)
    }
    if (p1col == "r" && p3col == "r"){
      p2.text = c.get("AllocationRR",p1idx,p3idx)
    }
    if (p1col == "b" && p3col == "b"){
      p2.text = c.get("AllocationBB",p1idx,p3idx)
    }
    a.add(p2,
            [name: "A",
             result:{
               p2.text = c.get("WaitAllocation")
               p2.neighbors.each{ n ->
                 if(n.score == p1idx){
                   n.truescore += 100
                 } else {
                   n.truescore += 200
                 }}}],
            [name: "B",
             result:{
               p2.text = c.get("WaitAllocation")
               p2.neighbors.each{ n ->
                 if(n.score == p1idx){
                   n.truescore += 180
                 } else {
                   n.truescore += 220
                 }}}])
  }
  
  g.V.filter{it.active && it.p1}.each { p1->
    
    p1.neighbors.each { n ->
      if(n.p2){ 
        p2idx = n.score
        p2col = n.col
      } else {
        p3idx = n.score
        p3col = n.col
      }
    }
    if (p2col == "r" && p3col == "b"){
      p1.text = c.get("AllocationRB",p1idx,p2idx)
    }
    if (p2col == "b" && p3col == "r"){
      p1.text = c.get("AllocationBR",p1idx,p2idx)
    }
    if (p2col == "r" && p3col == "r"){
      p1.text = c.get("AllocationRR",p1idx,p2idx)
    }
    if (p2col == "b" && p3col == "b"){
      p1.text = c.get("AllocationBB",p1idx,p2idx)
    }
    a.add(p1,
            [name: "A",
             result:{
               p1.text = c.get("WaitAllocation")
               p1.neighbors.each{ n ->
                 if(n.score == p2idx){
                   n.truescore += 100
                 } else {
                   n.truescore += 200
                 }}}],
            [name: "B",
             result:{
               p1.text = c.get("WaitAllocation")
               p1.neighbors.each{ n ->
                 if(n.score == p2idx){
                   n.truescore += 180
                 } else {
                   n.truescore += 220
                 }}}])
  } 
}

proposalStep.done = {
    println "proposalStep.done: ${curRound}"

  if(curRound < 3){
    curRound++
    pairingStep.start()

  } else {
    println "Done"
  }
}
