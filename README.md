# Agentske-tehnologije
## Projekat (Default zadatak 1)

### Tipovi agenata
- Agent sakupljac (Collector)
  - [x] Reaguje na ACL poruku
  - [x] Pretrazuje sajt koji je dobio u "content" polju ACL poruke
  - [x] Vrsi upis u odgovarajuci fajl (svaki sajt ima zaseban) 
  - [ ] Rezultati pretrage sajta
- Agent pretrazivac (Searcher)
  - [x] Reaguje na ACL poruku
  - [x] Trazi fajl u zavisnosti od "content" polja ACL poruke
  - [x] Cita sadrzaj fajla
  - [ ] Putem web socketa ispisuje klijentu rezultate pretrage
- Agent master (Master)
  - [x] Salje ACL poruku (jedini moze biti u ulozi "sender"-a)

### Podaci
 - [x] Lista pokrenutih agenata i konekcija (agentskih centara) se nalazi u Singleton beanu svakog agentskog centra

### Klijent - Server
- [x] GET /agents/classes – dobavi listu svih tipova agenata na sistemu
- [x] GET /agents/running – dobavi sve pokrenute agente sa sistema
- [x] PUT /agents/running/{type}/{name} – pokreni agenta određenog tipa sa zadatim imenom
- [x] DELETE /agents/running/{aid} – zaustavi određenog agenta
- [x] POST /messages – pošalji ACL poruku
- [x] GET /messages – dobavi listu performativa

### Server - server
- [x] POST /node - Nov ne-master čvor kontaktira master čvor koji ga registruje (...ConnectionManager) 
- [ ] GET /agents/classes - Master čvor traži spisak tipova agenata koje podržava nov ne-master
čvor 
- (*Problem: Uvek isti tipovi?*)
- [x] POST /node - Master čvor javlja ostalim ne-master čvorovima da je nov ne-master čvor ušao u mrezu (...ConnectionManager)
- [ ] POST /agents/classes - Master čvor dostavlja spisak novih tipova agenata (ukoliko ih ima)
ostalim ne- master čvorovima 
- (*Problem: Uvek isti tipovi?*)
- [ ] POST /nodes - Master čvor dostavlja spisak ostalih ne-master čvorova novom ne-master čvoru 
- (*Problem: Server u stanju Starting...*)
- [ ] POST /agents/classes - Master čvor dostavlja spisak tipova agenata novom ne-master čvoru
koje podržava on ili neki od ostalih ne-master čvorova 
- (*Problem: Uvek isti tipovi?*)
- [ ] POST /agents/running - Master salje listu pokrenutih agenata novom cvoru 
- (*Problem: Server u stanju Starting...*)
- [x] DELETE /node/{alias} - Brisanje cvora koji je ugasen
- [x] GET /node - handshake
