package com.egutter.trading.runner;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;

public class UrlDecoder {
    public static void main(String[] args) {
        try {
            String url= "https://127.0.0.1/?code=hO%2BGQtr4mrImiYh8oZqkfa%2BCRbEMupcnGVZMMgXj8jlTuXu3TNIyHsGBFc2v2IQxNmYY60E2T9GWg4YemcheTt8%2BR5wR%2BwyxhsqUMBQ3Apz3IvDgY%2Fq2bxJJWXoRRDU16b9dl5AYigraWxsclWB1E2xnpwNkjFwOzYHY8eHAdVuxloTVFjPOMOKTU%2BnJNrd54mZkrmyWdO6h6fAo4py29Z0En8dQz4GTHxF1lOlGKtZHUr%2B5LUtv2HyNeWx836p8riasQV0slJYX32z5Im9iK9vG2TTZun9kse%2FpQOrNy7VcW8sd5YsOar4hWRjHDZvK2ghxtbJ5UnG6ZO3HcNfO8Y%2F43YIBODTAyE5ICsCyY3gYAIGMYPvmVUBrKk30oT1Gs%2BxcUTMoMMEiJWHh%2B0Is14YNW%2B5pBgARcToJ16lzJEHuYT7FtzUaU5CMKaS100MQuG4LYrgoVi%2FJHHvlzC0RYd2KYS7zqkm%2BkXqnUIJkL5fJ6z4JpWLNPyU8LCR4qpNHlzEX8puMDg4gJQfNeuBQ%2BEPUoBOLUF1ixxZ5iHepS%2Bxzw5sWAJdxKovAc6s9ajc08sfzcij7scS1EOWaESICGYIE%2FhZmWBJLIDJ1tFeozafKrXtBBnXpXEEh0qPsLU3WIe1pIgbBz06eXzYmeVqA3RZfZLegeB4AO7Hcr5H7Uw3B2g2AG%2BIBbj9PhB2FzfrbsxWe7K5WAq25XFrfV6rBv3AYsv6Cc4Qlf5KetU8vbIX71TnosFAPSVbDO2NbwOCG2FwmNK64z8dLSMwZqjMYs1lpJZrE8jbl8aHtexTnw5y1159gKeK4uck4hlvYoFaKVUAI7CQREjBdptC40RkewRkA5LvaXB1XD%2BMBVtdzAxDZyhiFhkdOOGcaZumoO3gP7OpDyheCxuo%3D212FD3x19z9sWBHDJACbC00B75E";
            String result = java.net.URLDecoder.decode(url, StandardCharsets.UTF_8.name());
            System.out.println(result);
        } catch (UnsupportedEncodingException e) {
            // not going to happen - value came from JDK's own StandardCharsets
        }
    }
}
