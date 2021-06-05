package com.egutter.trading.stock;

import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.google.common.collect.Ordering;
import org.joda.time.Days;
import org.joda.time.LocalDate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public class StockMarket {

    public static final String TECH_SECTOR = "TECH";
    public static final String SPY_SECTOR = "SPY";
    public static final String COMM_SECTOR = "COMM";
    public static final String FINANCE_SECTOR = "FINANCE";
    public static final String CONS_BASIC_SECTOR = "CONS BASIC";
    public static final String CONS_DISC_SECTOR = "CONS DISC";
    public static final String HEALTH_SECTOR = "HEALTH";
    public static final String BIOTECH_SECTOR = "BIOTECH";
    public static final String INDUSTRIAL_SECTOR = "INDUSTRIAL";
    public static final String INNOVATION_SECTOR = "INNOVATION";
    public static final String ETF_SECTORS = "SECTORS ETF";
    public static final String METALS_SECTORS = "METALS";
    public static final String EMERGENT_SECTORS = "EMERGENT";
    public static final String SMALL_CAP_SECTORS = "SMALL CAP";
    public static final String GREEN_SECTORS = "GREEN ETF";
    public static final String DEVELOP_ETF_MARKETS = "DEVELOP ETF MARKETS";
    private List<StockPrices> stockPrices;

    private StockPrices marketIndexPrices;

    public static String MERVAL_MARKET_INDEX = "%5EMERV";
    private LocalDate lastTradingDay;
    private LocalDate firstTradingDay;

    public static void main(String[] args) {
        System.out.println(allSectorsStockSymbolsAsStockGroups());
    }

    public StockMarket(List<StockPrices> stockPrices, StockPrices marketIndexPrices) {
        this.stockPrices = stockPrices;
        this.marketIndexPrices = marketIndexPrices;
    }

    public StockMarket(List<StockPrices> stockPrices) {
        this.stockPrices = stockPrices;
        this.marketIndexPrices = new StockPrices("MKT");
    }

    public static List<StockGroup> onlyIndividualSectors() {
        List<StockGroup> stockGroups = new ArrayList<StockGroup>();
        stockGroups.addAll(Arrays.asList(new StockGroup("VGLT", longTermBonds()),
                new StockGroup("VCLT", corporateBonds()),
                new StockGroup(METALS_SECTORS, metals())));
        individualStocks().stream().forEach(stocks -> {
            stockGroups.add(new StockGroup(stocks[0], stocks));
        });

        return stockGroups;
    }

    public static List<StockGroup> spySector() {
        return Arrays.asList(new StockGroup(SPY_SECTOR, topSpy()));
    }

    public static List<StockGroup> allSectors() {
        List<StockGroup> stockGroups = new ArrayList<StockGroup>();
        stockGroups.addAll(Arrays.asList(new StockGroup(ETF_SECTORS, sectors()),
                new StockGroup(TECH_SECTOR, techSector()),
                new StockGroup(COMM_SECTOR, commSector()),
                new StockGroup(FINANCE_SECTOR, financeSector()),
                new StockGroup(CONS_BASIC_SECTOR, consumerBasicSector()),
                new StockGroup(CONS_DISC_SECTOR, consumerDiscSector()),
                new StockGroup(HEALTH_SECTOR, healthSector()),
                new StockGroup(BIOTECH_SECTOR, biotechSector()),
                new StockGroup(INDUSTRIAL_SECTOR, industrialSector()),
                new StockGroup(INNOVATION_SECTOR, innovationSector()),
                new StockGroup(METALS_SECTORS, metals()),
                new StockGroup(EMERGENT_SECTORS, emergentMarkets()),
                new StockGroup(GREEN_SECTORS, greenSector()),
                new StockGroup(SMALL_CAP_SECTORS, smallCapEtf()),
                new StockGroup(DEVELOP_ETF_MARKETS, developedMarkets()),
                new StockGroup("DEVELOP ADR", developAdr()),
                new StockGroup("ALL_SPY_500", sNp500()),
                new StockGroup("EMERG ADR", emergentAdr()),
                new StockGroup(SPY_SECTOR, topSpy())
                ));
        individualStocks().stream().forEach(stocks -> {
            stockGroups.add(new StockGroup(stocks[0], stocks));
        });
        return stockGroups;
    }

    public static String[] innovationSector() {
        return new String[]{
                "SQ",
                "Z",
                "SPLK",
                "TWLO",
                "WORK",
                "TSLA",
                "TWOU",
                "XLNX",
                "SPOT",
                "CRNC",
                "ZM",
                "NET",
                "IRBT",
                "DT",
                "ALB",
                "W",
                "ETSY",
                "VMW",
                "SHOP",
                "FPX"
        };
    }
    public static String[] newInnovationSector() {
        return new String[]{
                "MJ",
                "ROBO",
                "ARKK",
        };
    }

    public static String[] biotechSector() {
        return new String[]{
                "XBI",
                "VCEL",
                "UTHR",
                "NTLA",
                "ICPT",
                "CLVS",
                "MNKD",
                "CRIS",
                "GTHX",
                "CDNA",
                "AMGN",
                "CYTK",
                "GILD",
                "HGEN",
                "MGNX",
                "QURE",
                "DVAX",
                "DCPH",
                "AGIO",
                "INCY",
                "HRTX"
        };
    }
    public static String[] commSector() {
        return new String[]{
                "XLC",
                "FB",
                "GOOGL",
                "GOOG",
                "TMUS",
                "EA",
                "ATVI",
                "NFLX",
                "DIS",
                "CHTR",
                "CMCSA",
                "VZ",
                "TWTR",
                "T",
                "TTWO",
                "VIAC",
                "OMC",
                "FOXA",
                "IPG",
                "DISH",
                "DISCK"
        };
    }
    public static String[] industrialSector() {
        return new String[]{
                "XLI",
                "UNP",
                "HON",
                "BA",
                "RTX",
                "LMT",
                "MMM",
                "UPS",
                "CAT",
                "GE",
                "CSX",
                "ITW",
                "NOC",
                "DE",
                "NSC",
                "ROP",
                "LHX",
                "WM",
                "EMR",
                "GD",
                "ETN"
        };
    }
    public static String[] consumerDiscSector() {
        return new String[]{
                "XLY",
                "AMZN",
                "HD",
                "MCD",
                "NKE",
                "LOW",
                "SBUX",
                "BKNG",
                "TJX",
                "TGT",
                "DG",
                "EBAY",
                "GM",
                "ROST",
                "ORLY",
                "YUM",
                "AZO",
                "CMG",
                "MAR",
                "F",
                "HLT"
        };
    }

    public static String[] consumerBasicSector() {
        return new String[]{
                "XLP",
                "PG",
                "PEP",
                "KO",
                "WMT",
                "MO",
                "MDLZ",
                "COST",
                "CL",
                "KMB",
                "EL",
                "GIS",
                "WBA",
                "STZ",
                "SYY",
                "CLX",
                "MNST",
                "KR",
                "ADM",
                "MKC"
        };
    }
    public static String[] healthSector() {
        return new String[]{
                "XLV",
                "JNJ",
                "UNH",
                "MRK",
                "PFE",
                "ABBV",
                "ABT",
                "TMO",
                "AMGN",
                "LLY",
                "BMY",
                "MDT",
                "DHR",
                "GILD",
                "CVS",
                "VRTX",
                "CI",
                "BDX",
                "ISRG",
                "ANTM",
                "ZTS",
        };
    }
    public static String[] financeSector() {
        return new String[]{
                "XLF",
                "JPM",
                "BAC",
                "C",
                "WFC",
                "BLK",
                "SPGI",
                "AXP",
                "GS",
                "CME",
                "CB",
                "MS",
                "USB",
                "MMC",
                "TFC",
                "ICE",
                "PNC",
                "PGR",
                "AON",
                "MCO"
        };
    }
    public static String[] topTechSector() {
        return new String[]{
                "XLK",
                "MSFT",
                "AAPL",
                "V",
                "MA",
                "INTC",
                "NVDA",
                "ADBE",
                "PYPL",
                "CSCO",
                "CRM"
        };
    }

    public static String[] techSector() {
        return new String[]{
                "XLK",
                "MSFT",
                "AAPL",
                "V",
                "MA",
                "INTC",
                "NVDA",
                "ADBE",
                "PYPL",
                "CSCO",
                "CRM",
                "ACN",
                "AVGO",
                "TXN",
                "ORCL",
                "IBM",
                "QCOM",
                "FIS",
                "NOW",
                "INTU",
                "ADP"
        };
    }
    public static String[] developedMarkets() {
        return new String[]{
            "VTI",
            "VGK",
            "EPP",
            "VEA",
            "EWJ",
            "EWG",
            "EWQ",
            "EWU",
            "EWL",
            "EWC",
            "EIS",
        };
    }
    public static String[] europe() {
        return new String[]{
            "VGK",
        };
    }
    public static String[] topSpy() {
        return new String[]{
                "AAPL",
                "MSFT",
                "FB",
                "TSLA",
                "JNJ",
                "JPM",
                "V",
                "PG",
                "UNH",
                "DIS",
                "MA",
                "NVDA",
                "HD",
                "PYPL",
                "VZ",
                "ADBE",
                "CMCSA",
                "NFLX",
                "BAC",
                "KO",
                "MRK",
                "PEP",
                "T",
                "PFE",
                "WMT",
                "INTC",
                "ABT",
                "CRM",
                "ABBV",
                "TMO",
                "NKE",
                "AVGO",
                "XOM",
                "QCOM",
                "CSCO",
                "COST",
                "ACN",
                "CVX",
                "MCD",
                "MDT",
                "NEE",
                "TXN",
                "HON",
                "LLY",
                "DHR",
                "UNP",
                "BMY",
                "LIN",
                "AMGN",
                "PM",
                "C",
                "SBUX",
                "BA",
                "UPS",
                "WFC",
                "LOW",
                "ORCL",
                "IBM",
                "AMD",
                "RTX",
                "NOW",
                "MMM",
                "AMT",
                "MS",
                "BLK",
                "CAT",
                "ISRG",
                "GE",
                "INTU",
                "CHTR",
                "GS",
                "CVS",
                "TGT",
                "FIS",
                "LMT",
                "MU",
                "MDLZ",
                "SQ",
//                "SYK",
//                "SCHW",
//                "ANTM",
//                "SPGI",
//                "AMAT",
//                "ZTS",
//                "MO",
//                "DE",
//                "TMUS",
//                "CI",
//                "TJX",
//                "PLD",
//                "CL",
//                "GILD"
        };
    }
    public static String[] sectors() {
        return new String[]{
            "SPY",
            "XLF",
            "XLI",
            "XLK",
            "XLY",
            "XLV",
            "XLP",
            "XLC",
            "XBI",
        };
    }
    public static String[] metals() {
        return new String[]{
            "GLD",
            "SLV",
        };
    }
    public static String[] corporateBonds() {
        return new String[]{
            "VCLT"
        };
    }

    public static String[] longTermBonds() {
        return new String[]{
            "VGLT",
        };
    }
    public static String[] greenBonds() {
        return new String[]{
            "BGRN",
        };
    }
    public static String[] greenSector() {
        return new String[]{
            "PBW",
            "ICLN",
            "TAN",
            "QCLN",
            "FAN",
        };
    }

    public static String[] smallCapEtf() {
        return new String[]{
            "VBK",
            "SCZ",
                // PLUG,NVAX,PENN,CZR,RUN,DAR,RARE,IIVI,LAD,RDFN,MRTX,PACB,DECK,NTRA,BLDR,ARWR,RH,CHDN,APPN,FATE
        };
    }

    public static String[] smallCap() {
        return new String[]{
            "AMH",
            "AOS",
            "AVLR",
            "AVTR",
            "AXON",
            "BILL",
            "CHGG",
            "COLD",
            "CRL",
            "CTLT",
            "CZR",
            "DT",
            "ELS",
            "ENTG",
            "FIVN",
            "FND",
            "FWONK",
            "G",
            "GH",
            "GNRC",
            "HZNP",
            "NVCR",
            "OLED",
            "PEN",
            "PFPT",
            "POOL",
            "PTC",
            "RGEN",
            "RUN",
            "STE",
            "TDY",
            "WEX",
            "ZNGA"
        };
    }

    public static String[] midCap() {
        return new String[]{
            "IJH",
            "ACM",
            "AFG",
            "ATR",
            "AZPN",
            "BLDR",
            "BRO",
            "CABO",
            "CDAY",
            "CGNX",
            "CPT",
            "CREE",
            "CSL",
            "DAR",
            "DECK",
            "EWBC",
            "FDS",
            "FHN",
            "FICO",
            "FIVE",
            "GGG",
            "HUBB",
            "JAZZ",
            "JLL",
            "LAD",
            "LEA",
            "LII",
            "MASI",
            "MIDD",
            "MKSI",
            "MOH",
            "MPW",
            "NDSN",
            "OC",
            "PRAH",
            "RH",
            "RPM",
            "RS",
            "SAM",
            "SBNY",
            "SEDG",
            "STLD",
            "TECH",
            "TREX",
            "TTC",
            "UGI",
            "WSM",
            "WSO",
            "WTRG",
            "XPO",
            "Y"
        };
    }

    public static String[] govBonds() {
        return new String[]{
            "BLV",
            "VGLT",
            "BND",
            "EDV",
        };
    }

    public static String[] emergentMarkets() {
        return new String[]{
            "VWO",
            "MCHI",
            "EWT",
            "EWZ",
            "RSX",
            "INDA",
            "EWW",
            "ECH",
        };
    }

    public static String[] developAdr() {
        return new String[]{
            "SAP", // SAP
            "TM", // Toyota
            "NVS", // Novartis
            "UBS", // UBS
            "AZN", // Aztra Seneca
            "GSK", // Glaxo Smith
        };
    }

    public static String[] emergentAdr() {
        return new String[]{
            "MELI", // Mercado LIbre
            "GLOB", // Globant
            "BABA", // Ali Baba
            "BIDU", // Baidu
            "JD", // JD.com
        };
    }
    public static String[] largeSectorStocks() {
        return new String[]{
                "MSFT",
                "AAPL",
                "V",
                "MA",
                "INTC",
                "NVDA",
                "ADBE",
                "PYPL",
                "CSCO",
                "CRM",
                "ACN",
                "AVGO",
                "TXN",
                "ORCL",
                "IBM",
                "QCOM",
                "FIS",
                "NOW",
                "INTU",
                "ADP",
                "AMD",
                "FISV",
                "MU",
                "AMAT",
                "GPN",
                "ADSK",
                "LRCX",
                "ADI",
                "KLAC",
                "CTSH",
                "JPM",
                "BAC",
                "C",
                "WFC",
                "BLK",
                "SPGI",
                "AXP",
                "GS",
                "CME",
                "CB",
                "MS",
                "USB",
                "MMC",
                "TFC",
                "ICE",
                "PNC",
                "PGR",
                "AON",
                "MCO",
                "SCHW",
                "COF",
                "BK",
                "ALL",
                "TRV",
                "MET",
                "TROW",
                "AIG",
                "BRKB",
                "UNH",
                "HD",
                "BA",
                "MCD",
                "MMM",
                "JNJ",
                "CAT",
                "PG",
                "DIS",
                "WMT",
                "NKE",
                "KO",
                "CVX",
                "MRK",
                "RTX",
                "VZ",
                "XOM",
                "WBA",
                "PFE",
                "ABBV",
                "ABT",
                "TMO",
                "AMGN",
                "LLY",
                "BMY",
                "MDT",
                "DHR",
                "GILD",
                "CVS",
                "PEP",
                "MO",
                "MDLZ",
                "COST",
                "CL",
                "KMB",
                "EL",
                "GIS",
                "STZ",
                "AMZN",
                "LOW",
                "SBUX",
                "BKNG",
                "TJX",
                "TGT",
                "DG",
                "EBAY",
                "GM",
                "ROST",
                "ORLY",
                "YUM",
                "AZO",
                "CMG",
                "MAR",
                "F",
                "HLT",
                "DLTR",
                "VFC",
                "APTV",
                "DHI",
                "BBY",
                "FB",
                "GOOGL",
                "GOOG",
                "TMUS",
                "EA",
                "ATVI",
                "NFLX",
                "CHTR",
                "CMCSA",
                "TWTR",
                "T",
                "TTWO",
                "OMC",
                "UNP",
                "HON",
                "FDX",
                "UPS",
                "GE"
        };
    }

    public static String[] sNp20() {
        return new String[]{
                "MMM",
                "AAPL",
                "MSFT",
                "FB",
                "GOOG",
                "GOOGL",
                "AMZN",
                "NFLX",
                "JNJ",
                "V",
                "PG",
                "UNH",
                "JPM",
                "INTC",
                "HD",
                "MA",
                "VZ",
                "PFE",
                "T",
                "MRK",
                "NVDA",
                "DIS",
                "CSCO",
                "PEP",
                "XOM",
                "BAC",
                "WMT",
                "ADBE",
                "CVX",
                "PYPL",
                "KO",
                "CMCSA",
                "ABT",
        };
    }
    public static String[] sNp500() {
        return new String[]{
                "MMM",
                "ABT",
                "ABBV",
                "ABMD",
                "ACN",
                "ATVI",
                "ADBE",
                "AMD",
                "AAP",
                "AES",
                "AFL",
                "A",
                "APD",
                "AKAM",
                "ALK",
                "ALB",
                "ARE",
                "ALXN",
                "ALGN",
                "ALLE",
//                "AGN",
                "ADS",
                "LNT",
                "ALL",
                "GOOGL",
                "GOOG",
                "MO",
                "AMZN",
                "AMCR",
                "AEE",
                "AAL",
                "AEP",
                "AXP",
                "AIG",
                "AMT",
                "AWK",
                "AMP",
                "ABC",
                "AME",
                "AMGN",
                "APH",
                "ADI",
                "ANSS",
                "ANTM",
                "AON",
                "AOS",
                "APA",
                "AIV",
                "AAPL",
                "AMAT",
                "APTV",
                "ADM",
                "ANET",
                "AJG",
                "AIZ",
                "T",
                "ATO",
                "ADSK",
                "ADP",
                "AZO",
                "AVB",
                "AVY",
                "BKR",
                "BLL",
                "BAC",
                "BK",
                "BAX",
                "BDX",
//                "BRKB",
                "BBY",
                "BIIB",
                "BLK",
                "BA",
                "BKNG",
                "BWA",
                "BXP",
                "BSX",
                "BMY",
                "AVGO",
                "BR",
//                "BFB",
                "CHRW",
                "COG",
                "CDNS",
                "CPB",
                "COF",
                "CPRI",
                "CAH",
                "KMX",
                "CCL",
//                "CARR", new stock?
                "CAT",
                "CBOE",
                "CBRE",
                "CDW",
                "CE",
                "CNC",
                "CNP",
//                "CTL",
                "CERN",
                "CF",
                "SCHW",
                "CHTR",
                "CVX",
                "CMG",
                "CB",
                "CHD",
                "CI",
                "CINF",
                "CTAS",
                "CSCO",
                "C",
                "CFG",
                "CTXS",
                "CLX",
                "CME",
                "CMS",
                "KO",
                "CTSH",
                "CL",
                "CMCSA",
                "CMA",
                "CAG",
//                "CXO",
                "COP",
                "ED",
                "STZ",
                "COO",
                "CPRT",
                "GLW",
//                "CTVA", new stock?
                "COST",
                "COTY",
                "CCI",
                "CSX",
                "CMI",
                "CVS",
                "DHI",
                "DHR",
                "DRI",
                "DVA",
                "DE",
                "DAL",
                "XRAY",
                "DVN",
                "FANG",
                "DLR",
                "DFS",
                "DISCA",
                "DISCK",
                "DISH",
                "DG",
                "DLTR",
                "D",
                "DOV",
//                "DOW", new stock?
                "DTE",
                "DUK",
                "DRE",
                "DD",
                "DXC",
//                "ETFC",
                "EMN",
                "ETN",
                "EBAY",
                "ECL",
                "EIX",
                "EW",
                "EA",
                "EMR",
                "ETR",
                "EOG",
                "EFX",
                "EQIX",
                "EQR",
                "ESS",
                "EL",
                "EVRG",
                "ES",
                "RE",
                "EXC",
                "EXPE",
                "EXPD",
                "EXR",
                "XOM",
                "FFIV",
                "FB",
                "FAST",
                "FRT",
                "FDX",
                "FIS",
                "FITB",
                "FE",
                "FRC",
                "FISV",
                "FLT",
                "FLIR",
                "FLS",
                "FMC",
                "F",
                "FTNT",
                "FTV",
                "FBHS",
                "FOXA",
                "FOX",
                "BEN",
                "FCX",
                "GPS",
                "GRMN",
                "IT",
                "GD",
                "GE",
                "GIS",
                "GM",
                "GPC",
                "GILD",
                "GL",
                "GPN",
                "GS",
                "GWW",
                "HRB",
                "HAL",
                "HBI",
                "HOG",
                "HIG",
                "HAS",
                "HCA",
                "PEAK",
                "HP",
                "HSIC",
                "HSY",
                "HES",
                "HPE",
                "HLT",
                "HFC",
                "HOLX",
                "HD",
                "HON",
                "HRL",
                "HST",
//                "HWM",
                "HPQ",
                "HUM",
                "HBAN",
                "HII",
                "IEX",
                "IDXX",
                "INFO",
                "ITW",
                "ILMN",
                "INCY",
                "IR",
                "INTC",
                "ICE",
                "IBM",
                "IP",
                "IPG",
                "IFF",
                "INTU",
                "ISRG",
                "IVZ",
                "IPGP",
                "IQV",
                "IRM",
                "JKHY",
                "J",
                "JBHT",
                "SJM",
                "JNJ",
                "JCI",
                "JPM",
                "JNPR",
                "KSU",
                "K",
                "KEY",
                "KEYS",
                "KMB",
                "KIM",
                "KMI",
                "KLAC",
                "KSS",
                "KHC",
                "KR",
                "LB",
                "LHX",
                "LH",
                "LRCX",
                "LW",
                "LVS",
                "LEG",
                "LDOS",
                "LEN",
                "LLY",
                "LNC",
                "LIN",
                "LYV",
                "LKQ",
                "LMT",
                "L",
                "LOW",
                "LYB",
                "MTB",
                "MRO",
                "MPC",
                "MKTX",
                "MAR",
                "MMC",
                "MLM",
                "MAS",
                "MA",
                "MKC",
                "MXIM",
                "MCD",
                "MCK",
                "MDT",
                "MRK",
                "MET",
                "MTD",
                "MGM",
                "MCHP",
                "MU",
                "MSFT",
                "MAA",
                "MHK",
                "TAP",
                "MDLZ",
                "MNST",
                "MCO",
                "MS",
                "MOS",
                "MSI",
                "MSCI",
//                "MYL",
                "NDAQ",
                "NOV",
                "NTAP",
                "NFLX",
                "NWL",
                "NEM",
                "NWSA",
                "NWS",
                "NEE",
                "NLSN",
                "NKE",
                "NI",
//                "NBL",
                "JWN",
                "NSC",
                "NTRS",
                "NOC",
                "NLOK",
                "NCLH",
                "NRG",
                "NUE",
                "NVDA",
                "NVR",
                "ORLY",
                "OXY",
                "ODFL",
                "OMC",
                "OKE",
                "ORCL",
                "OTIS",
                "PCAR",
                "PKG",
                "PH",
                "PAYX",
                "PAYC",
                "PYPL",
                "PNR",
                "PBCT",
                "PEP",
                "PKI",
                "PRGO",
                "PFE",
                "PM",
                "PSX",
                "PNW",
                "PXD",
                "PNC",
                "PPG",
                "PPL",
                "PFG",
                "PG",
                "PGR",
                "PLD",
                "PRU",
                "PEG",
                "PSA",
                "PHM",
                "PVH",
                "QRVO",
                "PWR",
                "QCOM",
                "DGX",
                "RL",
                "RJF",
                "RTX",
                "O",
                "REG",
                "REGN",
                "RF",
                "RSG",
                "RMD",
                "RHI",
                "ROK",
                "ROL",
                "ROP",
                "ROST",
                "RCL",
                "SPGI",
                "CRM",
                "SBAC",
                "SLB",
                "STX",
                "SEE",
                "SRE",
                "NOW",
                "SHW",
                "SPG",
                "SWKS",
                "SLG",
                "SNA",
                "SO",
                "LUV",
                "SWK",
                "SBUX",
                "STT",
                "STE",
                "SYK",
                "SIVB",
                "SYF",
                "SNPS",
                "SYY",
                "TMUS",
                "TROW",
                "TTWO",
                "TPR",
                "TGT",
                "TEL",
                "FTI",
                "TFX",
                "TXN",
                "TXT",
                "TMO",
//                "TIF",
                "TJX",
                "TSCO",
//                "TT",
                "TDG",
                "TRV",
                "TFC",
                "TWTR",
                "TSN",
                "UDR",
                "ULTA",
                "USB",
                "UAA",
                "UA",
                "UNP",
                "UAL",
                "UNH",
                "UPS",
                "URI",
                "UHS",
                "UNM",
                "VFC",
                "VLO",
                "VAR",
                "VTR",
                "VRSN",
                "VRSK",
                "VZ",
                "VRTX",
//                "VIAC",
                "V",
                "VNO",
                "VMC",
                "WRB",
                "WAB",
                "WMT",
                "WBA",
                "DIS",
                "WM",
                "WAT",
                "WEC",
                "WFC",
                "WELL",
                "WDC",
                "WU",
                "WRK",
                "WY",
                "WHR",
                "WMB",
                "WLTW",
                "WYNN",
                "XEL",
                "XRX",
                "XLNX",
                "XYL",
                "YUM",
                "ZBRA",
                "ZBH",
                "ZION",
                "ZTS"
        };
//        List<String> excludeStocks = Arrays.asList("AGN", "FCX", "GPS", "GD", "HD", "HON", "HII", "ISRG", "JKHY", "KEYS", "KR", "LKQ", "MMC", "MAA", "NFLX", "NOC", "NVR", "OMC", "PAYC", "PPG", "PPL", "PG", "DGX", "RTX", "RSG", "SBAC", "SPG", "SIVB", "SYF", "TXT", "TDG", "UDR", "UNH", "VZ", "VIAC", "WRB", "WM", "WEC", "WU", "WMB", "WLTW", "WYNN", "XRX");
//        return Arrays.stream(sNp500).filter(s -> !excludeStocks.contains(s)).toArray(String[]::new);
    }


    public static String[] spy() {
        return new String[] {"SPY"};
    }

    public static List<String[]> individualStocks() {
        return Arrays.asList(aapl(), amazon(), microsoft(), google(), netflix(), tesla(), salesforce(), paypal(), intel(), nvidia(), electronicArts(), square(), cloudfare(), splunk(), cisco());
    }

    public static String[] aapl() {
        return new String[] {"AAPL"};
    }

    public static String[] amazon() {
        return new String[] { "AMZN"};
    }

    public static String[] microsoft() {
        return new String[] { "MSFT"};
    }
    public static String[] google() {
        return new String[] { "GOOGL"};
    }

    public static String[] netflix() {
        return new String[] { "NFLX"};
    }
    public static String[] tesla() {
        return new String[] { "TSLA"};
    }

    public static String[] salesforce() {
        return new String[] { "CRM"};
    }

    public static String[] paypal() {
        return new String[] { "PYPL"};
    }

    public static String[] intel() {
        return new String[] { "INTC"};
    }

    public static String[] nvidia() {
        return new String[] { "NVDA"};
    }

    public static String[] electronicArts() {
        return new String[] { "EA"};
    }

    public static String[] square() {
        return new String[] { "SQ"};
    }

    public static String[] cloudfare() {
        return new String[] { "NET"};
    }
    public static String[] splunk() {
        return new String[] { "SPLK"};
    }

    public static String[] cisco() {
        return new String[] { "CSCO"};
    }

    public static List<String> allSectorsStockSymbols() {
        return allSectors().stream().map(stockGroup -> Arrays.asList(stockGroup.getStockSymbols())).flatMap(Collection::stream).
                distinct().collect(Collectors.toList());
    }

    public static List<StockGroup> allSectorsStockSymbolsAsStockGroups() {
        return allSectors().stream().map(stockGroup -> Arrays.asList(stockGroup.getStockSymbols())).
                flatMap(Collection::stream).
                distinct().
                map(symbol -> new StockGroup(symbol, new String[]{symbol})).
                collect(Collectors.toList());
    }

    public List<StockPrices> getStockPrices() {
        return stockPrices;
    }

    public StockPrices getStockPricesFor(String stockName) {
        return stockPrices.stream().filter(stockPrices -> stockPrices.getStockName().equals(stockName)).findFirst().get();
    }

    public LocalDate getLastTradingDay() {
        if (this.lastTradingDay == null) {
            this.lastTradingDay = Ordering.natural().max(tradingDays(stockPrices -> stockPrices.getLastTradingDate()));;
        }
        return this.lastTradingDay;
    }

    public LocalDate getFirstTradingDay() {
        if (this.firstTradingDay == null) {
            this.firstTradingDay = Ordering.natural().min(tradingDays(stockPrices -> stockPrices.getFirstTradingDate()));
        }
        return this.firstTradingDay;
    }

    private List<LocalDate> tradingDays(Function<StockPrices, LocalDate> extractor) {
        return Lists.transform(stockPrices, new Function<StockPrices, LocalDate>() {
            @Override
            public LocalDate apply(StockPrices stockPrices) {
                return extractor.apply(stockPrices);
            }
        });
    }

    public StockPrices getMarketIndexPrices() {
        return marketIndexPrices;
    }

    public static String[] allStockSymbols() {
        return Stream.concat(Arrays.stream(merval25StockSymbols()), Arrays.stream(altStockSymbols()))
                .toArray(String[]::new);
    }

    public static String[] merval25StockSymbols() {
        return new String[]{
                // MERVAL 25
                "AGRO.BA", // AGROMETAL
                "ALUA.BA", // ALUAR ALUMINIO
                "APBR.BA", // PETROBRAS ORDINARIAS
                "BBAR.BA", // BCO.FRANCES S.A.
                "BHIP.BA", // BANCO HIPOTECARIO
                "BMA.BA", // BANCO MACRO
                "BYMA.BA", // BYMA
                "BPAT.BA", // Banco Patagonia
                "BRIO.BA", // Banco Santander Rio S.A.
                "CAPU.BA", // Caputo S.A
                "CECO2.BA", // Endesa Costanera SA
                "CELU.BA", // Celulosa Argentina S.A.
                "CEPU.BA", // CENTRAL PUERTO
                "COME.BA", // COMERCIAL DEL PLATA
                "CRES.BA", // CRESUD
                "CTIO.BA", // Consultatio
                "CVH.BA", // Cablevision
                "DGCU2.BA", // Distribuidora de Gas cuyana
                "EDN.BA", // EDENOR
                "TXAR.BA", // Ternium Argentina
                "GGAL.BA", // GRUPO FINANCIERO GALICIA
                "INDU.BA", // SOLVAY INDUPA SAI
                "IRSA.BA", //Inversiones y Representaciones Sociedad Anónima
                "LEDE.BA",
                "LOMA.BA", // Loma Negra
                "MIRG.BA", // Mirgor S.A.C.I.F.I.A.
                "METR.BA", // Metrogas
                "MOLI.BA", // MOLINOS
                "PAMP.BA", // PAMPA ENERGIA
                "SAMI.BA", // SAn Miguel
                "SEMI.BA", // Molino Juan Semino
                "TECO2.BA", // TELECOM ARGENTINA
                "TEF.BA",   // Telefonica
                "TGNO4.BA", // TRANSPORT GAS NORTE
                "TGSU2.BA", // Transportadora de Gas Del Sur S.A.
                "TRAN.BA", // CIA.TRANSP.EN.ELECTRICIDAD
                "VALO.BA", // Valores
                "YPFD.BA", // YPF
                MERVAL_MARKET_INDEX
        };
    }

    public static String[] altStockSymbols() {
        return new String[]{
                // OTRAS
                "APSA.BA",
                "AUSO.BA",
                "BOLT.BA",
                "CADO.BA",
                "CAPX.BA",
                "CARC.BA",
                "CGPA2.BA",
                "COLO.BA",
                "DYCA.BA",
                "ESME.BA",
                "ESTR.BA",
                "FERR.BA",
                "FIPL.BA",
                "GARO.BA",
                "GBAN.BA",
                "GCLA.BA",
                "GRIM.BA",
                "INTR.BA",
                "INVJ.BA",
                "JMIN.BA",
                "LONG.BA",
                "METR.BA",
                "MORI.BA",
                "OEST.BA",
                "PATA.BA",
                "PATY.BA",
                "POLL.BA",
                "PSUR.BA",
                "REP.BA",
                "REP.BA",
                "RIGO.BA",
                "ROSE.BA",
                "STD.BA",
                "TGLT.BA",
        };
    }

    public static boolean isMerval25(String stockName) {
        return Arrays.asList(merval25StockSymbols()).contains(stockName);
    }

    public int getTotalTradingDays() {
        return Days.daysBetween(getFirstTradingDay(), getLastTradingDay()).getDays();
    }
}
