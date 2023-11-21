# Eksamen PGR301 2023

# Oppgave 1 A:

### For sensor : 

Etter å ha opprettet en fork av dette prosjektet, gå til repository instillinger og finn "secrets"

Legg til: 
* AWS_ACCESS_KEY_ID  
* AWS_SECRET_ACCESS_KEY 
* EMAIL_NAME(som det skal sendes alarm til. ref. oppgave 3B)
* AWS_REGION -> eu-west-1

**Kort begrunnelse for måleinstrumenter brukt:(oppgave 4A)**
1. Timer på hvor lang tid applikasjonen bruker på å kjøre en analyse. Tenker dette er verdiful informasjon
da man kan etterhvert som applikasjonen vokser så får man sett hva som tar lengst tid og om man eventuelt må gjøre noen endringer
i koden for å forbedre hastighet(f.eks - gruppere bilder og kjøre flere analyser på en gang en å kjøre en og en)
2. En Gauge - hvor man kan sjekke hvor mange "violations" det er registrert akkurat nå - ett slags snapshot av applikasjone
3. En counter på hvor mange "violations" som har skjedd siden applikasjonen startet - bra for å følge med på trender, når det skjer mest brudd og 
effektiviteten av eventuelle sikkerhetstiltak man vil gjøre for å forbedre antall "violations".

**Redgjørelse for valg av kriterier av CloudWatch alarm(oppgave 4B)**

Valgte å basere alarmen på hvis det totale nummeret av "violations" overstiger 5 så vill man få en e-post sendt.
Det er en god grunn til å bruke dette fordi om man opplever en unormal økning av sikkerhetsbrudd så vil man bli varslet.

# **Oppgave 4 - Drøfteoppgaver**
###  *A. Kontinuerlig integrering*

Kontinuelrlig integrasjon er en tilnærming innen programvareutvikling der utviklere jevnlig integrerer den nye
koden de har skrevet gjennom hele utviklingsprosessen , og legger den til i kodebasen minst en gang om dagen.
Automatiserte tester kjøres for hver versjon av programvaren for å oppdage
eventuelle problemer med integrering tidlig i prosessen, når det er enklere å rette dem.
Samlet sett hjelper kontinuerlig integrasjon med å effektivisere byggeprosessen, noe
som resulterer i programvare av høyere kvalitet og mer forutsigbare leveringsplaner.

 **Hvordan jobber vi med CI i GitHub rent praktisk?**
 
1. Starte med å opprette en felles Github-repository
2. Lag CI-workflows: Definere CI-workflows ved hjelp av GitHub Actions. bygging av koden, automatiserte tester, distribusjon
3. Automatiserte tester: sjekke kodekvalietet og fuksjonalitet.(enhetstester, integrasjonstester, akseptansetester)
4. Pull requests(PR): Fullført en oppgave , opprett en PR. Github actions kan automatisk bygge og teste koden i PR-en for å sikre at den er i orden 
5. Kodeandmeldelse: Teammedlemmene kan gjennomgå og godkjenne PR-ene.CI prosessen hjelper til med å identifisere potensielle problemer- sånn at man kan løse de før de blir lagt ut.
6. Kontinuerlig leveranse: Når koden i PR er godkjent og CI vellykket , kan man implementere den inn i hovedgrenen, og endringene kan automatisk distribueres til produksjonen.

### *B. Sammenligning av Scrum/Smidig og DevOps fra et utviklers perspektiv*

**1 .Scrum/Smidig Metodikk**

Smidig metodikk er en prosjektledelsesramme som deler prosjekter opp i flere dynamiske faser, som ofte kalles sprinter.
1. Plan
2. Design
3. Develop
4. Test
5. Deploy
6. review

Det er en iterativ metodikk, etter hver sprint reflekterer teamene og ser tilbake for å se om det var noe som kunne forbedres, slik at de kan justere
stratergien sin for neste sprint. 

*Fordeler ved å bruke Scrum/Smidig*

- **Fleksibilitet:** 
  - Scrum tilpasser seg endringer raskt
- **Tidlig og kontinuerlig leveranse:**
  - scrum sikrer at fungerende deler av produktet er tilgjengelige tidlig i prosjektet , noe som gir mulighet for tidlig tilbakemelding
- **Samarbeid og kommunikasjon**
  - Scrum oppmuntrer til tett sammarbeid mellom temmedlemmer, noe som kan bidra til bedre forståelse av krav og mål
- **Fokus på kvalitet**
  - kontinuerlig integrasjon og automatiserte testing er en integrert del av scrum noe som fører til høyere kodekvalitet

*Ulemper ved å bruke Scrum/Smidig*

- **Endringsresistens:**
  - Noen kan ha vanskeligheter med å tilpasse seg smidige metoder og endre eksisterende arbeidskultur
- **Manglende tydelige krav:**
  - i prosjekter der kravene er uklare eller endres hyppig, kan det være vanskelig å opprettholde kontinuerlig leveranse og stabilitet
- **Tidspress:**
  - Sprinter har faste tidsfrister, noe som kan føre til press på teamet for å levere innenfor tidsrammen
- **Kompleksitet:**
  - i for store eller komplekse prosjekter kan scrum være utfordrende å implementere effektivt

**2. DevOps Metodikk:**

Grunnleggende prinsipper og prksiser: 

**1. Samarbeid:**
   - Oppfordrer til sammarbeid mellom utviklings- og driftsteamene, hvor de tradisjonelt har operert separat. Men i DevOps jobber de tett sammen gjennom hele prosessen.

**2. Automatisering:**
   - Ett viktig og sentralt konsept innen DevOps. Automatisere det meste for å redusere manuelle feil.Dette inkluderer: bygging, testing, distrubusjon og vedlikehold av programvare

**3. Kontinuerlig integrasjon:**
- Handler om å integrere kodeendringer regelmessig, ofte flere ganger om dagen, dette pga at feil skap oppdages tidlig og kan bli løst raskt

**4. Kontinuerlig levering:**
- Automatisere leveransen av kodeendringer til produsjonsmiljøet når de er klar, etter vellykket testing

**5. Overvåking og tilbakemelding:**
- Aktiv overvåking av programvaren i produksjon, sånn at eventuelle problemer kan oppdages og løses forløpende

Kvalieteten og leveransetempo:

- DevOps fremmer kvalietet gjennom automatiserte tester, tidlig oppdagelse og retting av feil og kontinuerlig overvåking i produsjon. Dette reduserer risikoen for feil i produksjon
og fører til bedre kvelitetskontroll.

- Ved å automatisere prosesser, redusere manuelle trinn og tillate raskere iterasjoner, kan DevOps dramatisk akselerere leveringshastigheten. Dette gjør det mulig å raskt levere nye funksjoner og oppdateringer til brukerne.

Styrker ved DevOps er som nevnt at det gir hyppigere og raskere levering av programvareendringer, og disse består ofte av høyere kvalietet på grunn av 
automatiseringen, minimerer nedetid og gjør at man oppdager feil tidlig. DevOps fremmer tett sammarbeid mellom de ulike teamene som fører til bedre forståelse og mulig mer effektiv løsning av problemer.

Noen utfordringer ved DevOps er at det er et komplektst system som kan gjøre at ved implementering hvor det er eksisterende systemer som har en eldre infrastruktur så kan dette bli komplekst.
Det vil innebære ett kulturskifte , spesielt i bedriftkulturen hvor da sammarbeid og automatisering vil bli prioritert. Man må huske på å integrere sikkerhet i DevOps-prosessen, ellers så kan den raske utrullingen av programvareendringer medføre en
økt sikkerhetsrisiko.


 **3.Sammenligning og kontrast:**

Scrum fremmer sammarbeid og kvalitetskontroll gjennom regelmessige revisjoner og testing. Dette er kritisk for å oppdage og løse feil tidlig i utviklingsprosessen,
noe som bidrar til å opprettholde programvarekvalitet og minimere kostbare feil som kan oppstå senere i utviklingen. Scrum er spesielt egnet for situasjoner som krever
fleksibilitet og hyppige iterasjoner, som for eksempel utviklingen av nye produkter, nettutviklingsprosjekter og utvikling av
innholdsstyringssystemer(CMS), der kravene kan endre seg over tid og samarbeid er avgjørende

På den andre siden legger DevOps vekt på automatisering og rask levering, noe som er avgjørende for å oppnå raskere tid til markedet og tilpasse seg endringer.
Automatisering av bygge-,test og disribusjonsprosesser gjør det mulig å akselerere leveransetempoet betydelig, noe som er ideelt for situasjoner som krever hyppige oppdateringer
og rask respons på endrig i brukerbehov. DevOps er ofte benyttet innen områder som kontinuerlig distrbusjon, netthandel, cloud applikasjoner, mobilapp-utvikling og infrastruktur som kode. 
Disse bruksområdene drar nytte av DevOps-prinsipper for å oppnå¨automatisering, pålitlighet og hurtig skalering.

Det er viktig å merke seg at valget mellom Scrum og DevOps avhjenger av prosjektets spesifikke behov og bedriftskulturen. I noen tilfeller kan det være hensiktsmessig
å integrere elementer fra begge metodene for å oppnå en balanse mellom kvalitetskontroll og hastighet i utviklingen. Samtidig er erfaring og tilpassing viktige
faktorer for å oppnå suksess uansett hvilken tilnærming som velges.

### C. Det Andre Prinsippet - *Feedback*

Feedback prinsipper jeg ville brukt er alarmer og monitorering
ved hjelp av CloudWatch Metrics og CloudWatch Alarms for realtidsmetrikk og varsler om ytelsesproblemer. Jeg ville også benyttet ekstern overvåking med for eksempel StatusCake for å sikre tilgjengeligheten fra ulike steder rundt om i verden.
For å få innsikt i ytelsen, hadde jeg samlet inn metrikker med for eksempel Spring Boot, Micrometer, Grafana og InfluxDB, men også forrettnigns og tilpassede metrikker for å forstå hvordan funksjonaliteten blir brukt. Når det gjelder testing, 
ville jeg utført tester som enhetstester, integrasjonstester og automatiserte tester for å validere funksjonaliteten. Loadtesting med K6 og ytelsestesting i CI/CD-pipelinen er også en del av prosessen for å sikre at programvaren håndterer 
belastning og oppfyller ytelsesmål. 

Alle disse feedback-mekanismene integreres i ulike stadier av utviklingslivssyklusen, fra planlegging og design til implementering, testing og distribusjon. Dette hjelper meg med å oppnå 
kontinuerlig forbedring, identifisere feil tidlig i prosessen og levere høy kvalitet gjennom hele utviklingen, samtidig som jeg tilpasser meg brukernes behov.



