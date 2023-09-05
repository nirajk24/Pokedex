package com.affinity.pokedex.utility

object NameToId {

    val nameToIdMap = mapOf(
        "Bulbasaur" to "#001",
        "Ivysaur" to "#002",
        "Venusaur" to "#003",
        "Charmander" to "#004",
        "Charmeleon" to "#005",
        "Charizard" to "#006",
        "Squirtle" to "#007",
        "Wartortle" to "#008",
        "Blastoise" to "#009",
        "Caterpie" to "#010",
        "Metapod" to "#011",
        "Butterfree" to "#012",
        "Weedle" to "#013",
        "Kakuna" to "#014",
        "Beedrill" to "#015",
        "Pidgey" to "#016",
        "Pidgeotto" to "#017",
        "Pidgeot" to "#018",
        "Rattata" to "#019",
        "Raticate" to "#020",
        "Spearow" to "#021",
        "Fearow" to "#022",
        "Ekans" to "#023",
        "Arbok" to "#024",
        "Pikachu" to "#025",
        "Raichu" to "#026",
        "Sandshrew" to "#027",
        "Sandslash" to "#028",
        "Alolan Sandslash" to "#028",
        "Nidoran♀" to "#029",
        "Nidorina" to "#030",
        "Nidoqueen" to "#031",
        "Nidoran♂" to "#032",
        "Nidorino" to "#033",
        "Nidoking" to "#034",
        "Clefairy" to "#035",
        "Clefable" to "#036",
        "Vulpix" to "#037",
        "Ninetales" to "#038",
        "Jigglypuff" to "#039",
        "Wigglytuff" to "#040",
        "Zubat" to "#041",
        "Golbat" to "#042",
        "Oddish" to "#043",
        "Gloom" to "#044",
        "Vileplume" to "#045",
        "Paras" to "#046",
        "Parasect" to "#047",
        "Venonat" to "#048",
        "Venomoth" to "#049",
        "Diglett" to "#050",
        "Dugtrio" to "#051",
        "Meowth" to "#052",
        "Persian" to "#053",
        "Psyduck" to "#054",
        "Golduck" to "#055",
        "Mankey" to "#056",
        "Primeape" to "#057",
        "Growlithe" to "#058",
        "Arcanine" to "#059",
        "Poliwag" to "#060",
        "Poliwhirl" to "#061",
        "Poliwrath" to "#062",
        "Abra" to "#063",
        "Kadabra" to "#064",
        "Alakazam" to "#065",
        "Machop" to "#066",
        "Machoke" to "#067",
        "Machamp" to "#068",
        "Bellsprout" to "#069",
        "Weepinbell" to "#070",
        "Victreebel" to "#071",
        "Tentacool" to "#072",
        "Tentacruel" to "#073",
        "Geodude" to "#074",
        "Graveler" to "#075",
        "Golem" to "#076",
        "Ponyta" to "#077",
        "Rapidash" to "#078",
        "Slowpoke" to "#079",
        "Slowbro" to "#080",
        "Magnemite" to "#081",
        "Magneton" to "#082",
        "Farfetchd" to "#083",
        "Farfetch'd" to "#083",
        "Doduo" to "#084",
        "Dodrio" to "#085",
        "Seel" to "#086",
        "Dewgong" to "#087",
        "Grimer" to "#088",
        "Muk" to "#089",
        "Shellder" to "#090",
        "Cloyster" to "#091",
        "Gastly" to "#092",
        "Haunter" to "#093",
        "Gengar" to "#094",
        "Onix" to "#095",
        "Drowzee" to "#096",
        "Hypno" to "#097",
        "Krabby" to "#098",
        "Kingler" to "#099",
        "Voltorb" to "#100",
        "Electrode" to "#101",
        "Exeggcute" to "#102",
        "Exeggutor" to "#103",
        "Cubone" to "#104",
        "Marowak" to "#105",
        "Hitmonlee" to "#106",
        "Hitmonchan" to "#107",
        "Lickitung" to "#108",
        "Koffing" to "#109",
        "Weezing" to "#110",
        "Rhyhorn" to "#111",
        "Rhydon" to "#112",
        "Chansey" to "#113",
        "Tangela" to "#114",
        "Kangaskhan" to "#115",
        "Horsea" to "#116",
        "Seadra" to "#117",
        "Goldeen" to "#118",
        "Seaking" to "#119",
        "Staryu" to "#120",
        "Starmie" to "#121",
        "Mr. Mime" to "#122",
        "Scyther" to "#123",
        "Jynx" to "#124",
        "Electabuzz" to "#125",
        "Magmar" to "#126",
        "Pinsir" to "#127",
        "Tauros" to "#128",
        "Magikarp" to "#129",
        "Gyarados" to "#130",
        "Lapras" to "#131",
        "Ditto" to "#132",
        "Eevee" to "#133",
        "Vaporeon" to "#134",
        "Jolteon" to "#135",
        "Flareon" to "#136",
        "Porygon" to "#137",
        "Omanyte" to "#138",
        "Omastar" to "#139",
        "Kabuto" to "#140",
        "Kabutops" to "#141",
        "Aerodactyl" to "#142",
        "Snorlax" to "#143",
        "Articuno" to "#144",
        "Zapdos" to "#145",
        "Moltres" to "#146",
        "Dratini" to "#147",
        "Dragonair" to "#148",
        "Dragonite" to "#149",
        "Mewtwo" to "#150",
        "Mew" to "#151",
        "Chikorita" to "#152",
        "Bayleef" to "#153",
        "Meganium" to "#154",
        "Cyndaquil" to "#155",
        "Quilava" to "#156",
        "Typhlosion" to "#157",
        "Totodile" to "#158",
        "Croconaw" to "#159",
        "Feraligatr" to "#160",
        "Sentret" to "#161",
        "Furret" to "#162",
        "Hoothoot" to "#163",
        "Noctowl" to "#164",
        "Ledyba" to "#165",
        "Ledian" to "#166",
        "Spinarak" to "#167",
        "Ariados" to "#168",
        "Crobat" to "#169",
        "Chinchou" to "#170",
        "Lanturn" to "#171",
        "Pichu" to "#172",
        "Cleffa" to "#173",
        "Igglybuff" to "#174",
        "Togepi" to "#175",
        "Togetic" to "#176",
        "Natu" to "#177",
        "Xatu" to "#178",
        "Mareep" to "#179",
        "Flaaffy" to "#180",
        "Ampharos" to "#181",
        "Bellossom" to "#182",
        "Marill" to "#183",
        "Azumarill" to "#184",
        "Sudowoodo" to "#185",
        "Politoed" to "#186",
        "Hoppip" to "#187",
        "Skiploom" to "#188",
        "Jumpluff" to "#189",
        "Aipom" to "#190",
        "Sunkern" to "#191",
        "Sunflora" to "#192",
        "Yanma" to "#193",
        "Wooper" to "#194",
        "Quagsire" to "#195",
        "Espeon" to "#196",
        "Umbreon" to "#197",
        "Murkrow" to "#198",
        "Slowking" to "#199",
        "Misdreavus" to "#200",
        "Unown" to "#201",
        "Wobbuffet" to "#202",
        "Girafarig" to "#203",
        "Pineco" to "#204",
        "Forretress" to "#205",
        "Dunsparce" to "#206",
        "Gligar" to "#207",
        "Steelix" to "#208",
        "Snubbull" to "#209",
        "Granbull" to "#210",
        "Qwilfish" to "#211",
        "Scizor" to "#212",
        "Shuckle" to "#213",
        "Heracross" to "#214",
        "Sneasel" to "#215",
        "Teddiursa" to "#216",
        "Ursaring" to "#217",
        "Slugma" to "#218",
        "Magcargo" to "#219",
        "Swinub" to "#220",
        "Piloswine" to "#221",
        "Corsola" to "#222",
        "Remoraid" to "#223",
        "Octillery" to "#224",
        "Delibird" to "#225",
        "Mantine" to "#226",
        "Skarmory" to "#227",
        "Houndour" to "#228",
        "Houndoom" to "#229",
        "Kingdra" to "#230",
        "Phanpy" to "#231",
        "Donphan" to "#232",
        "Porygon2" to "#233",
        "Stantler" to "#234",
        "Smeargle" to "#235",
        "Tyrogue" to "#236",
        "Hitmontop" to "#237",
        "Smoochum" to "#238",
        "Elekid" to "#239",
        "Magby" to "#240",
        "Miltank" to "#241",
        "Blissey" to "#242",
        "Raikou" to "#243",
        "Entei" to "#244",
        "Suicune" to "#245",
        "Larvitar" to "#246",
        "Pupitar" to "#247",
        "Tyranitar" to "#248",
        "Lugia" to "#249",
        "Ho-Oh" to "#250",
        "Celebi" to "#251",
        "Treecko" to "#252",
        "Grovyle" to "#253",
        "Sceptile" to "#254",
        "Torchic" to "#255",
        "Combusken" to "#256",
        "Blaziken" to "#257",
        "Mudkip" to "#258",
        "Marshtomp" to "#259",
        "Swampert" to "#260",
        "Poochyena" to "#261",
        "Mightyena" to "#262",
        "Zigzagoon" to "#263",
        "Linoone" to "#264",
        "Wurmple" to "#265",
        "Silcoon" to "#266",
        "Beautifly" to "#267",
        "Cascoon" to "#268",
        "Dustox" to "#269",
        "Lotad" to "#270",
        "Lombre" to "#271",
        "Ludicolo" to "#272",
        "Seedot" to "#273",
        "Nuzleaf" to "#274",
        "Shiftry" to "#275",
        "Taillow" to "#276",
        "Swellow" to "#277",
        "Wingull" to "#278",
        "Pelipper" to "#279",
        "Ralts" to "#280",
        "Kirlia" to "#281",
        "Gardevoir" to "#282",
        "Surskit" to "#283",
        "Masquerain" to "#284",
        "Shroomish" to "#285",
        "Breloom" to "#286",
        "Slakoth" to "#287",
        "Vigoroth" to "#288",
        "Slaking" to "#289",
        "Nincada" to "#290",
        "Ninjask" to "#291",
        "Shedinja" to "#292",
        "Whismur" to "#293",
        "Loudred" to "#294",
        "Exploud" to "#295",
        "Makuhita" to "#296",
        "Hariyama" to "#297",
        "Azurill" to "#298",
        "Nosepass" to "#299",
        "Skitty" to "#300",
        "Delcatty" to "#301",
        "Sableye" to "#302",
        "Mawile" to "#303",
        "Aron" to "#304",
        "Lairon" to "#305",
        "Aggron" to "#306",
        "Meditite" to "#307",
        "Medicham" to "#308",
        "Electrike" to "#309",
        "Manectric" to "#310",
        "Plusle" to "#311",
        "Minun" to "#312",
        "Volbeat" to "#313",
        "Illumise" to "#314",
        "Roselia" to "#315",
        "Gulpin" to "#316",
        "Swalot" to "#317",
        "Carvanha" to "#318",
        "Sharpedo" to "#319",
        "Wailmer" to "#320",
        "Wailord" to "#321",
        "Numel" to "#322",
        "Camerupt" to "#323",
        "Torkoal" to "#324",
        "Spoink" to "#325",
        "Grumpig" to "#326",
        "Spinda" to "#327",
        "Trapinch" to "#328",
        "Vibrava" to "#329",
        "Flygon" to "#330",
        "Cacnea" to "#331",
        "Cacturne" to "#332",
        "Swablu" to "#333",
        "Altaria" to "#334",
        "Zangoose" to "#335",
        "Seviper" to "#336",
        "Lunatone" to "#337",
        "Solrock" to "#338",
        "Barboach" to "#339",
        "Whiscash" to "#340",
        "Corphish" to "#341",
        "Crawdaunt" to "#342",
        "Baltoy" to "#343",
        "Claydol" to "#344",
        "Lileep" to "#345",
        "Cradily" to "#346",
        "Anorith" to "#347",
        "Armaldo" to "#348",
        "Feebas" to "#349",
        "Milotic" to "#350",
        "Castform" to "#351",
        "Kecleon" to "#352",
        "Shuppet" to "#353",
        "Banette" to "#354",
        "Duskull" to "#355",
        "Dusclops" to "#356",
        "Tropius" to "#357",
        "Chimecho" to "#358",
        "Absol" to "#359",
        "Wynaut" to "#360",
        "Snorunt" to "#361",
        "Glalie" to "#362",
        "Spheal" to "#363",
        "Sealeo" to "#364",
        "Walrein" to "#365",
        "Clamperl" to "#366",
        "Huntail" to "#367",
        "Gorebyss" to "#368",
        "Relicanth" to "#369",
        "Luvdisc" to "#370",
        "Bagon" to "#371",
        "Shelgon" to "#372",
        "Salamence" to "#373",
        "Beldum" to "#374",
        "Metang" to "#375",
        "Metagross" to "#376",
        "Regirock" to "#377",
        "Regice" to "#378",
        "Registeel" to "#379",
        "Latias" to "#380",
        "Latios" to "#381",
        "Kyogre" to "#382",
        "Groudon" to "#383",
        "Rayquaza" to "#384",
        "Jirachi" to "#385",
        "Deoxys" to "#386",
        "Turtwig" to "#387",
        "Grotle" to "#388",
        "Torterra" to "#389",
        "Chimchar" to "#390",
        "Monferno" to "#391",
        "Infernape" to "#392",
        "Piplup" to "#393",
        "Prinplup" to "#394",
        "Empoleon" to "#395",
        "Starly" to "#396",
        "Staravia" to "#397",
        "Staraptor" to "#398",
        "Bidoof" to "#399",
        "Bibarel" to "#400",
        "Kricketot" to "#401",
        "Kricketune" to "#402",
        "Shinx" to "#403",
        "Luxio" to "#404",
        "Luxray" to "#405",
        "Budew" to "#406",
        "Roserade" to "#407",
        "Cranidos" to "#408",
        "Rampardos" to "#409",
        "Shieldon" to "#410",
        "Bastiodon" to "#411",
        "Burmy" to "#412",
        "Wormadam" to "#413",
        "Mothim" to "#414",
        "Combee" to "#415",
        "Vespiquen" to "#416",
        "Pachirisu" to "#417",
        "Buizel" to "#418",
        "Floatzel" to "#419",
        "Cherubi" to "#420",
        "Cherrim" to "#421",
        "Shellos" to "#422",
        "Gastrodon" to "#423",
        "Ambipom" to "#424",
        "Drifloon" to "#425",
        "Drifblim" to "#426",
        "Buneary" to "#427",
        "Lopunny" to "#428",
        "Mismagius" to "#429",
        "Honchkrow" to "#430",
        "Glameow" to "#431",
        "Purugly" to "#432",
        "Chingling" to "#433",
        "Stunky" to "#434",
        "Skuntank" to "#435",
        "Bronzor" to "#436",
        "Bronzong" to "#437",
        "Bonsly" to "#438",
        "Mime Jr." to "#439",
        "Happiny" to "#440",
        "Chatot" to "#441",
        "Spiritomb" to "#442",
        "Gible" to "#443",
        "Gabite" to "#444",
        "Garchomp" to "#445",
        "Munchlax" to "#446",
        "Riolu" to "#447",
        "Lucario" to "#448",
        "Hippopotas" to "#449",
        "Hippowdon" to "#450",
        "Skorupi" to "#451",
        "Drapion" to "#452",
        "Croagunk" to "#453",
        "Toxicroak" to "#454",
        "Carnivine" to "#455",
        "Finneon" to "#456",
        "Lumineon" to "#457",
        "Mantyke" to "#458",
        "Snover" to "#459",
        "Abomasnow" to "#460",
        "Weavile" to "#461",
        "Magnezone" to "#462",
        "Lickilicky" to "#463",
        "Rhyperior" to "#464",
        "Tangrowth" to "#465",
        "Electivire" to "#466",
        "Magmortar" to "#467",
        "Togekiss" to "#468",
        "Yanmega" to "#469",
        "Leafeon" to "#470",
        "Glaceon" to "#471",
        "Gliscor" to "#472",
        "Mamoswine" to "#473",
        "Porygon-Z" to "#474",
        "Gallade" to "#475",
        "Probopass" to "#476",
        "Dusknoir" to "#477",
        "Froslass" to "#478",
        "Rotom" to "#479",
        "Uxie" to "#480",
        "Mesprit" to "#481",
        "Azelf" to "#482",
        "Dialga" to "#483",
        "Palkia" to "#484",
        "Heatran" to "#485",
        "Regigigas" to "#486",
        "Giratina" to "#487",
        "Cresselia" to "#488",
        "Phione" to "#489",
        "Manaphy" to "#490",
        "Darkrai" to "#491",
        "Shaymin" to "#492",
        "Arceus" to "#493",
        "Victini" to "#494",
        "Snivy" to "#495",
        "Servine" to "#496",
        "Serperior" to "#497",
        "Tepig" to "#498",
        "Pignite" to "#499",
        "Emboar" to "#500",
        "Oshawott" to "#501",
        "Dewott" to "#502",
        "Samurott" to "#503",
        "Patrat" to "#504",
        "Watchog" to "#505",
        "Lillipup" to "#506",
        "Herdier" to "#507",
        "Stoutland" to "#508",
        "Purrloin" to "#509",
        "Liepard" to "#510",
        "Pansage" to "#511",
        "Simisage" to "#512",
        "Pansear" to "#513",
        "Simisear" to "#514",
        "Panpour" to "#515",
        "Simipour" to "#516",
        "Munna" to "#517",
        "Musharna" to "#518",
        "Pidove" to "#519",
        "Tranquill" to "#520",
        "Unfezant" to "#521",
        "Blitzle" to "#522",
        "Zebstrika" to "#523",
        "Roggenrola" to "#524",
        "Boldore" to "#525",
        "Gigalith" to "#526",
        "Woobat" to "#527",
        "Swoobat" to "#528",
        "Drilbur" to "#529",
        "Excadrill" to "#530",
        "Audino" to "#531",
        "Timburr" to "#532",
        "Gurdurr" to "#533",
        "Conkeldurr" to "#534",
        "Tympole" to "#535",
        "Palpitoad" to "#536",
        "Seismitoad" to "#537",
        "Throh" to "#538",
        "Sawk" to "#539",
        "Sewaddle" to "#540",
        "Swadloon" to "#541",
        "Leavanny" to "#542",
        "Venipede" to "#543",
        "Whirlipede" to "#544",
        "Scolipede" to "#545",
        "Cottonee" to "#546",
        "Whimsicott" to "#547",
        "Petilil" to "#548",
        "Lilligant" to "#549",
        "Basculin" to "#550",
        "Sandile" to "#551",
        "Krokorok" to "#552",
        "Krookodile" to "#553",
        "Darumaka" to "#554",
        "Darmanitan" to "#555",
        "Maractus" to "#556",
        "Dwebble" to "#557",
        "Crustle" to "#558",
        "Scraggy" to "#559",
        "Scrafty" to "#560",
        "Sigilyph" to "#561",
        "Yamask" to "#562",
        "Cofagrigus" to "#563",
        "Tirtouga" to "#564",
        "Carracosta" to "#565",
        "Archen" to "#566",
        "Archeops" to "#567",
        "Trubbish" to "#568",
        "Garbodor" to "#569",
        "Zorua" to "#570",
        "Zoroark" to "#571",
        "Minccino" to "#572",
        "Cinccino" to "#573",
        "Gothita" to "#574",
        "Gothorita" to "#575",
        "Gothitelle" to "#576",
        "Solosis" to "#577",
        "Duosion" to "#578",
        "Reuniclus" to "#579",
        "Ducklett" to "#580",
        "Swanna" to "#581",
        "Vanillite" to "#582",
        "Vanillish" to "#583",
        "Vanilluxe" to "#584",
        "Deerling" to "#585",
        "Sawsbuck" to "#586",
        "Emolga" to "#587",
        "Karrablast" to "#588",
        "Escavalier" to "#589",
        "Foongus" to "#590",
        "Amoonguss" to "#591",
        "Frillish" to "#592",
        "Jellicent" to "#593",
        "Alomomola" to "#594",
        "Joltik" to "#595",
        "Galvantula" to "#596",
        "Ferroseed" to "#597",
        "Ferrothorn" to "#598",
        "Klink" to "#599",
        "Klang" to "#600",
        "Klinklang" to "#601",
        "Tynamo" to "#602",
        "Eelektrik" to "#603",
        "Eelektross" to "#604",
        "Elgyem" to "#605",
        "Beheeyem" to "#606",
        "Litwick" to "#607",
        "Lampent" to "#608",
        "Chandelure" to "#609",
        "Axew" to "#610",
        "Fraxure" to "#611",
        "Haxorus" to "#612",
        "Cubchoo" to "#613",
        "Beartic" to "#614",
        "Cryogonal" to "#615",
        "Shelmet" to "#616",
        "Accelgor" to "#617",
        "Stunfisk" to "#618",
        "Mienfoo" to "#619",
        "Mienshao" to "#620",
        "Druddigon" to "#621",
        "Golett" to "#622",
        "Golurk" to "#623",
        "Pawniard" to "#624",
        "Bisharp" to "#625",
        "Bouffalant" to "#626",
        "Rufflet" to "#627",
        "Braviary" to "#628",
        "Vullaby" to "#629",
        "Mandibuzz" to "#630",
        "Heatmor" to "#631",
        "Durant" to "#632",
        "Deino" to "#633",
        "Zweilous" to "#634",
        "Hydreigon" to "#635",
        "Larvesta" to "#636",
        "Volcarona" to "#637",
        "Cobalion" to "#638",
        "Terrakion" to "#639",
        "Virizion" to "#640",
        "Tornadus" to "#641",
        "Thundurus" to "#642",
        "Reshiram" to "#643",
        "Zekrom" to "#644",
        "Landorus" to "#645",
        "Kyurem" to "#646",
        "Keldeo" to "#647",
        "Meloetta" to "#648",
        "Genesect" to "#649",
        "Chespin" to "#650",
        "Quilladin" to "#651",
        "Chesnaught" to "#652",
        "Fennekin" to "#653",
        "Braixen" to "#654",
        "Delphox" to "#655",
        "Froakie" to "#656",
        "Frogadier" to "#657",
        "Greninja" to "#658",
        "Bunnelby" to "#659",
        "Diggersby" to "#660",
        "Fletchling" to "#661",
        "Fletchinder" to "#662",
        "Talonflame" to "#663",
        "Scatterbug" to "#664",
        "Spewpa" to "#665",
        "Vivillon" to "#666",
        "Litleo" to "#667",
        "Pyroar" to "#668",
        "Flabébé" to "#669",
        "Floette" to "#670",
        "Florges" to "#671",
        "Skiddo" to "#672",
        "Gogoat" to "#673",
        "Pancham" to "#674",
        "Pangoro" to "#675",
        "Furfrou" to "#676",
        "Espurr" to "#677",
        "Meowstic" to "#678",
        "Honedge" to "#679",
        "Doublade" to "#680",
        "Aegislash" to "#681",
        "Spritzee" to "#682",
        "Aromatisse" to "#683",
        "Swirlix" to "#684",
        "Slurpuff" to "#685",
        "Inkay" to "#686",
        "Malamar" to "#687",
        "Binacle" to "#688",
        "Barbaracle" to "#689",
        "Skrelp" to "#690",
        "Dragalge" to "#691",
        "Clauncher" to "#692",
        "Clawitzer" to "#693",
        "Helioptile" to "#694",
        "Heliolisk" to "#695",
        "Tyrunt" to "#696",
        "Tyrantrum" to "#697",
        "Amaura" to "#698",
        "Aurorus" to "#699",
        "Sylveon" to "#700",
        "Hawlucha" to "#701",
        "Dedenne" to "#702",
        "Carbink" to "#703",
        "Goomy" to "#704",
        "Sliggoo" to "#705",
        "Goodra" to "#706",
        "Klefki" to "#707",
        "Phantump" to "#708",
        "Trevenant" to "#709",
        "Pumpkaboo" to "#710",
        "Gourgeist" to "#711",
        "Bergmite" to "#712",
        "Avalugg" to "#713",
        "Noibat" to "#714",
        "Noivern" to "#715",
        "Xerneas" to "#716",
        "Yveltal" to "#717",
        "Zygarde" to "#718",
        "Diancie" to "#719",
        "Hoopa" to "#720",
        "Volcanion" to "#721",
        "Rowlet" to "#722",
        "Dartrix" to "#723",
        "Decidueye" to "#724",
        "Litten" to "#725",
        "Torracat" to "#726",
        "Incineroar" to "#727",
        "Popplio" to "#728",
        "Brionne" to "#729",
        "Primarina" to "#730",
        "Pikipek" to "#731",
        "Trumbeak" to "#732",
        "Toucannon" to "#733",
        "Yungoos" to "#734",
        "Gumshoos" to "#735",
        "Grubbin" to "#736",
        "Charjabug" to "#737",
        "Vikavolt" to "#738",
        "Crabrawler" to "#739",
        "Crabominable" to "#740",
        "Oricorio" to "#741",
        "Cutiefly" to "#742",
        "Ribombee" to "#743",
        "Rockruff" to "#744",
        "Lycanroc" to "#745",
        "Wishiwashi" to "#746",
        "Mareanie" to "#747",
        "Toxapex" to "#748",
        "Mudbray" to "#749",
        "Mudsdale" to "#750",
        "Dewpider" to "#751",
        "Araquanid" to "#752",
        "Fomantis" to "#753",
        "Lurantis" to "#754",
        "Morelull" to "#755",
        "Shiinotic" to "#756",
        "Salandit" to "#757",
        "Salazzle" to "#758",
        "Stufful" to "#759",
        "Bewear" to "#760",
        "Bounsweet" to "#761",
        "Steenee" to "#762",
        "Tsareena" to "#763",
        "Comfey" to "#764",
        "Oranguru" to "#765",
        "Passimian" to "#766",
        "Wimpod" to "#767",
        "Golisopod" to "#768",
        "Sandygast" to "#769",
        "Palossand" to "#770",
        "Pyukumuku" to "#771",
        "Type: Null" to "#772",
        "Silvally" to "#773",
        "Minior" to "#774",
        "Komala" to "#775",
        "Turtonator" to "#776",
        "Togedemaru" to "#777",
        "Mimikyu" to "#778",
        "Bruxish" to "#779",
        "Drampa" to "#780",
        "Dhelmise" to "#781",
        "Jangmo-o" to "#782",
        "Hakamo-o" to "#783",
        "Kommo-o" to "#784",
        "Tapu Koko" to "#785",
        "Tapu Lele" to "#786",
        "Tapu Bulu" to "#787",
        "Tapu Fini" to "#788",
        "Cosmog" to "#789",
        "Cosmoem" to "#790",
        "Solgaleo" to "#791",
        "Lunala" to "#792",
        "Nihilego" to "#793",
        "Buzzwole" to "#794",
        "Pheromosa" to "#795",
        "Xurkitree" to "#796",
        "Celesteela" to "#797",
        "Kartana" to "#798",
        "Guzzlord" to "#799",
        "Necrozma" to "#800",
        "Magearna" to "#801",
        "Marshadow" to "#802",
        "Poipole" to "#803",
        "Naganadel" to "#804",
        "Stakataka" to "#805",
        "Blacephalon" to "#806",
        "Zeraora" to "#807",
        "Meltan" to "#808",
        "Melmetal" to "#809"
    )
}