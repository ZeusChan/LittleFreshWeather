package com.zeuschan.littlefreshweather.model.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by chenxiong on 2016/5/30.
 */
public class CityWeatherResponse {
    @Expose @SerializedName("HeWeather data service 3.0") private List<CityWeatherInfo> cityWeatherInfos;

    public List<CityWeatherInfo> getCityWeatherInfos() {
        return cityWeatherInfos;
    }

    public void setCityWeatherInfos(List<CityWeatherInfo> cityWeatherInfos) {
        this.cityWeatherInfos = cityWeatherInfos;
    }

    @Override
    public String toString() {
        return "CityWeatherResponse{" +
                "cityWeatherInfos=" + cityWeatherInfos +
                '}';
    }

    public class CityWeatherInfo extends BaseResponse {
        @Expose @SerializedName("basic") private Basic basic;
        @Expose @SerializedName("now") private Now now;
        @Expose @SerializedName("daily_forecast") private List<DailyForecast> daily_forecast;
        @Expose @SerializedName("suggestion") private Suggestion suggestion;
        @Expose @SerializedName("aqi") private Aqi aqi;

        @Override
        public String toString() {
            return "CityWeatherInfo{" +
                    "aqi=" + aqi +
                    ", basic=" + basic +
                    ", now=" + now +
                    ", daily_forecast=" + daily_forecast +
                    ", suggestion=" + suggestion +
                    '}';
        }

        public Aqi getAqi() {
            return aqi;
        }

        public void setAqi(Aqi aqi) {
            this.aqi = aqi;
        }

        public Basic getBasic() {
            return basic;
        }

        public void setBasic(Basic basic) {
            this.basic = basic;
        }

        public List<DailyForecast> getDaily_forecast() {
            return daily_forecast;
        }

        public void setDaily_forecast(List<DailyForecast> daily_forecast) {
            this.daily_forecast = daily_forecast;
        }

        public Now getNow() {
            return now;
        }

        public void setNow(Now now) {
            this.now = now;
        }

        public Suggestion getSuggestion() {
            return suggestion;
        }

        public void setSuggestion(Suggestion suggestion) {
            this.suggestion = suggestion;
        }

        public class Basic {
            @Expose @SerializedName("id") private String id;
            @Expose @SerializedName("cnty") private String country;
            @Expose @SerializedName("city") private String city;
            @Expose @SerializedName("update") private Update update;

            @Override
            public String toString() {
                return "Basic{" +
                        "city='" + city + '\'' +
                        ", id='" + id + '\'' +
                        ", country='" + country + '\'' +
                        ", update=" + update +
                        '}';
            }

            public String getCity() {
                return city;
            }

            public void setCity(String city) {
                this.city = city;
            }

            public String getCountry() {
                return country;
            }

            public void setCountry(String country) {
                this.country = country;
            }

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public Update getUpdate() {
                return update;
            }

            public void setUpdate(Update update) {
                this.update = update;
            }

            public class Update {
                @Expose @SerializedName("loc") private String loc;
                @Expose @SerializedName("utc") private String utc;

                @Override
                public String toString() {
                    return "Update{" +
                            "loc='" + loc + '\'' +
                            ", utc='" + utc + '\'' +
                            '}';
                }

                public String getLoc() {
                    return loc;
                }

                public void setLoc(String loc) {
                    this.loc = loc;
                }

                public String getUtc() {
                    return utc;
                }

                public void setUtc(String utc) {
                    this.utc = utc;
                }
            }
        }

        public class Now {
            @Expose @SerializedName("cond") private Cond cond;
            @Expose @SerializedName("wind") private Wind wind;
            @Expose @SerializedName("fl") private String fl;
            @Expose @SerializedName("hum") private String hum;
            @Expose @SerializedName("pcpn") private String pcpn;
            @Expose @SerializedName("pres") private String pres;
            @Expose @SerializedName("tmp") private String tmp;
            @Expose @SerializedName("vis") private String vis;

            public Cond getCond() {
                return cond;
            }

            public void setCond(Cond cond) {
                this.cond = cond;
            }

            public String getFl() {
                return fl;
            }

            public void setFl(String fl) {
                this.fl = fl;
            }

            public String getHum() {
                return hum;
            }

            public void setHum(String hum) {
                this.hum = hum;
            }

            public String getPcpn() {
                return pcpn;
            }

            public void setPcpn(String pcpn) {
                this.pcpn = pcpn;
            }

            public String getPres() {
                return pres;
            }

            public void setPres(String pres) {
                this.pres = pres;
            }

            public String getTmp() {
                return tmp;
            }

            public void setTmp(String tmp) {
                this.tmp = tmp;
            }

            public String getVis() {
                return vis;
            }

            public void setVis(String vis) {
                this.vis = vis;
            }

            public Wind getWind() {
                return wind;
            }

            public void setWind(Wind wind) {
                this.wind = wind;
            }

            @Override
            public String toString() {
                return "Now{" +
                        "cond=" + cond +
                        ", wind=" + wind +
                        ", fl=" + fl +
                        ", hum=" + hum +
                        ", pcpn=" + pcpn +
                        ", pres=" + pres +
                        ", tmp=" + tmp +
                        ", vis=" + vis +
                        '}';
            }

            public class Cond {
                @Expose @SerializedName("code") private String code;
                @Expose @SerializedName("txt") private String txt;

                public String getCode() {
                    return code;
                }

                public void setCode(String code) {
                    this.code = code;
                }

                public String getTxt() {
                    return txt;
                }

                public void setTxt(String txt) {
                    this.txt = txt;
                }

                @Override
                public String toString() {
                    return "Cond{" +
                            "code=" + code +
                            ", txt='" + txt + '\'' +
                            '}';
                }
            }

            public class Wind {
                @Expose @SerializedName("deg") private String deg;
                @Expose @SerializedName("dir") private String dir;
                @Expose @SerializedName("sc") private String sc;
                @Expose @SerializedName("spd") private String spd;

                public String getDeg() {
                    return deg;
                }

                public void setDeg(String deg) {
                    this.deg = deg;
                }

                public String getDir() {
                    return dir;
                }

                public void setDir(String dir) {
                    this.dir = dir;
                }

                public String getSc() {
                    return sc;
                }

                public void setSc(String sc) {
                    this.sc = sc;
                }

                public String getSpd() {
                    return spd;
                }

                public void setSpd(String spd) {
                    this.spd = spd;
                }

                @Override
                public String toString() {
                    return "Wind{" +
                            "deg=" + deg +
                            ", dir='" + dir + '\'' +
                            ", sc='" + sc + '\'' +
                            ", spd=" + spd +
                            '}';
                }
            }
        }

        public class DailyForecast {
            @Expose @SerializedName("astro") private Astro astro;
            @Expose @SerializedName("cond") private Cond cond;
            @Expose @SerializedName("tmp") private Tmp tmp;
            @Expose @SerializedName("wind") private Wind wind;
            @Expose @SerializedName("date") private String date;
            @Expose @SerializedName("hum") private String hum;
            @Expose @SerializedName("pcpn") private String pcpn;
            @Expose @SerializedName("pop") private String pop;
            @Expose @SerializedName("pres") private String pres;
            @Expose @SerializedName("vis") private String vis;

            public Astro getAstro() {
                return astro;
            }

            public void setAstro(Astro astro) {
                this.astro = astro;
            }

            public Cond getCond() {
                return cond;
            }

            public void setCond(Cond cond) {
                this.cond = cond;
            }

            public String getDate() {
                return date;
            }

            public void setDate(String date) {
                this.date = date;
            }

            public String getHum() {
                return hum;
            }

            public void setHum(String hum) {
                this.hum = hum;
            }

            public String getPcpn() {
                return pcpn;
            }

            public void setPcpn(String pcpn) {
                this.pcpn = pcpn;
            }

            public String getPop() {
                return pop;
            }

            public void setPop(String pop) {
                this.pop = pop;
            }

            public String getPres() {
                return pres;
            }

            public void setPres(String pres) {
                this.pres = pres;
            }

            public Tmp getTmp() {
                return tmp;
            }

            public void setTmp(Tmp tmp) {
                this.tmp = tmp;
            }

            public String getVis() {
                return vis;
            }

            public void setVis(String vis) {
                this.vis = vis;
            }

            public Wind getWind() {
                return wind;
            }

            public void setWind(Wind wind) {
                this.wind = wind;
            }

            @Override
            public String toString() {
                return "DailyForecast{" +
                        "astro=" + astro +
                        ", cond=" + cond +
                        ", tmp=" + tmp +
                        ", wind=" + wind +
                        ", date='" + date + '\'' +
                        ", hum=" + hum +
                        ", pcpn=" + pcpn +
                        ", pop=" + pop +
                        ", pres=" + pres +
                        ", vis=" + vis +
                        '}';
            }

            public class Astro {
                @Expose @SerializedName("sr") private String sr;
                @Expose @SerializedName("ss") private String ss;

                public String getSr() {
                    return sr;
                }

                public void setSr(String sr) {
                    this.sr = sr;
                }

                public String getSs() {
                    return ss;
                }

                public void setSs(String ss) {
                    this.ss = ss;
                }

                @Override
                public String toString() {
                    return "Astro{" +
                            "sr='" + sr + '\'' +
                            ", ss='" + ss + '\'' +
                            '}';
                }
            }

            public class Cond {
                @Expose @SerializedName("code_d") private String code_d;
                @Expose @SerializedName("code_n") private String code_n;
                @Expose @SerializedName("txt_d") private String txt_d;
                @Expose @SerializedName("txt_n") private String txt_n;

                @Override
                public String toString() {
                    return "Cond{" +
                            "code_d=" + code_d +
                            ", code_n=" + code_n +
                            ", txt_d='" + txt_d + '\'' +
                            ", txt_n='" + txt_n + '\'' +
                            '}';
                }

                public String getCode_d() {
                    return code_d;
                }

                public void setCode_d(String code_d) {
                    this.code_d = code_d;
                }

                public String getCode_n() {
                    return code_n;
                }

                public void setCode_n(String code_n) {
                    this.code_n = code_n;
                }

                public String getTxt_d() {
                    return txt_d;
                }

                public void setTxt_d(String txt_d) {
                    this.txt_d = txt_d;
                }

                public String getTxt_n() {
                    return txt_n;
                }

                public void setTxt_n(String txt_n) {
                    this.txt_n = txt_n;
                }
            }

            public class Tmp {
                @Expose @SerializedName("max") private String max;
                @Expose @SerializedName("min") private String min;

                @Override
                public String toString() {
                    return "Tmp{" +
                            "max=" + max +
                            ", min=" + min +
                            '}';
                }

                public String getMax() {
                    return max;
                }

                public void setMax(String max) {
                    this.max = max;
                }

                public String getMin() {
                    return min;
                }

                public void setMin(String min) {
                    this.min = min;
                }
            }

            public class Wind {
                @Expose @SerializedName("deg") private String deg;
                @Expose @SerializedName("dir") private String dir;
                @Expose @SerializedName("sc") private String sc;
                @Expose @SerializedName("spd") private String spd;

                @Override
                public String toString() {
                    return "Wind{" +
                            "deg=" + deg +
                            ", dir='" + dir + '\'' +
                            ", sc='" + sc + '\'' +
                            ", spd=" + spd +
                            '}';
                }

                public String getDeg() {
                    return deg;
                }

                public void setDeg(String deg) {
                    this.deg = deg;
                }

                public String getDir() {
                    return dir;
                }

                public void setDir(String dir) {
                    this.dir = dir;
                }

                public String getSc() {
                    return sc;
                }

                public void setSc(String sc) {
                    this.sc = sc;
                }

                public String getSpd() {
                    return spd;
                }

                public void setSpd(String spd) {
                    this.spd = spd;
                }
            }
        }

        public class Suggestion {
            @Expose @SerializedName("comf") private Comf comf;
            @Expose @SerializedName("cw") private Cw cw;
            @Expose @SerializedName("drsg") private Drsg drsg;
            @Expose @SerializedName("flu") private Flu flu;
            @Expose @SerializedName("sport") private Sport sport;
            @Expose @SerializedName("trav") private Trav trav;
            @Expose @SerializedName("uv") private Uv uv;

            @Override
            public String toString() {
                return "Suggestion{" +
                        "comf=" + comf +
                        ", cw=" + cw +
                        ", drsg=" + drsg +
                        ", flu=" + flu +
                        ", sport=" + sport +
                        ", trav=" + trav +
                        ", uv=" + uv +
                        '}';
            }

            public Comf getComf() {
                return comf;
            }

            public void setComf(Comf comf) {
                this.comf = comf;
            }

            public Cw getCw() {
                return cw;
            }

            public void setCw(Cw cw) {
                this.cw = cw;
            }

            public Drsg getDrsg() {
                return drsg;
            }

            public void setDrsg(Drsg drsg) {
                this.drsg = drsg;
            }

            public Flu getFlu() {
                return flu;
            }

            public void setFlu(Flu flu) {
                this.flu = flu;
            }

            public Sport getSport() {
                return sport;
            }

            public void setSport(Sport sport) {
                this.sport = sport;
            }

            public Trav getTrav() {
                return trav;
            }

            public void setTrav(Trav trav) {
                this.trav = trav;
            }

            public Uv getUv() {
                return uv;
            }

            public void setUv(Uv uv) {
                this.uv = uv;
            }

            public class Comf {
                @Expose @SerializedName("brf") private String brf;
                @Expose @SerializedName("txt") private String txt;

                @Override
                public String toString() {
                    return "Comf{" +
                            "brf='" + brf + '\'' +
                            ", txt='" + txt + '\'' +
                            '}';
                }

                public String getBrf() {
                    return brf;
                }

                public void setBrf(String brf) {
                    this.brf = brf;
                }

                public String getTxt() {
                    return txt;
                }

                public void setTxt(String txt) {
                    this.txt = txt;
                }
            }

            public class Cw {
                @Expose @SerializedName("brf") private String brf;
                @Expose @SerializedName("txt") private String txt;

                @Override
                public String toString() {
                    return "Cw{" +
                            "brf='" + brf + '\'' +
                            ", txt='" + txt + '\'' +
                            '}';
                }

                public String getBrf() {
                    return brf;
                }

                public void setBrf(String brf) {
                    this.brf = brf;
                }

                public String getTxt() {
                    return txt;
                }

                public void setTxt(String txt) {
                    this.txt = txt;
                }
            }

            public class Drsg {
                @Expose @SerializedName("brf") private String brf;
                @Expose @SerializedName("txt") private String txt;

                @Override
                public String toString() {
                    return "Drsg{" +
                            "brf='" + brf + '\'' +
                            ", txt='" + txt + '\'' +
                            '}';
                }

                public String getBrf() {
                    return brf;
                }

                public void setBrf(String brf) {
                    this.brf = brf;
                }

                public String getTxt() {
                    return txt;
                }

                public void setTxt(String txt) {
                    this.txt = txt;
                }
            }

            public class Flu {
                @Expose @SerializedName("brf") private String brf;
                @Expose @SerializedName("txt") private String txt;

                @Override
                public String toString() {
                    return "Flu{" +
                            "brf='" + brf + '\'' +
                            ", txt='" + txt + '\'' +
                            '}';
                }

                public String getBrf() {
                    return brf;
                }

                public void setBrf(String brf) {
                    this.brf = brf;
                }

                public String getTxt() {
                    return txt;
                }

                public void setTxt(String txt) {
                    this.txt = txt;
                }
            }

            public class Sport {
                @Expose @SerializedName("brf") private String brf;
                @Expose @SerializedName("txt") private String txt;

                @Override
                public String toString() {
                    return "Sport{" +
                            "brf='" + brf + '\'' +
                            ", txt='" + txt + '\'' +
                            '}';
                }

                public String getBrf() {
                    return brf;
                }

                public void setBrf(String brf) {
                    this.brf = brf;
                }

                public String getTxt() {
                    return txt;
                }

                public void setTxt(String txt) {
                    this.txt = txt;
                }
            }

            public class Trav {
                @Expose @SerializedName("brf") private String brf;
                @Expose @SerializedName("txt") private String txt;

                @Override
                public String toString() {
                    return "Trav{" +
                            "brf='" + brf + '\'' +
                            ", txt='" + txt + '\'' +
                            '}';
                }

                public String getBrf() {
                    return brf;
                }

                public void setBrf(String brf) {
                    this.brf = brf;
                }

                public String getTxt() {
                    return txt;
                }

                public void setTxt(String txt) {
                    this.txt = txt;
                }
            }

            public class Uv {
                @Expose @SerializedName("brf") private String brf;
                @Expose @SerializedName("txt") private String txt;

                @Override
                public String toString() {
                    return "Trav{" +
                            "brf='" + brf + '\'' +
                            ", txt='" + txt + '\'' +
                            '}';
                }

                public String getBrf() {
                    return brf;
                }

                public void setBrf(String brf) {
                    this.brf = brf;
                }

                public String getTxt() {
                    return txt;
                }

                public void setTxt(String txt) {
                    this.txt = txt;
                }
            }
        }

        public class Aqi {
            @Expose @SerializedName("city") private City city;

            @Override
            public String toString() {
                return "Aqi{" +
                        "city=" + city +
                        '}';
            }

            public City getCity() {
                return city;
            }

            public void setCity(City city) {
                this.city = city;
            }

            public class City {
                @Expose @SerializedName("aqi") private String aqi;
                @Expose @SerializedName("co") private String co;
                @Expose @SerializedName("no2") private String no2;
                @Expose @SerializedName("o3") private String o3;
                @Expose @SerializedName("pm10") private String pm10;
                @Expose @SerializedName("pm25") private String pm25;
                @Expose @SerializedName("qlty") private String qlty;
                @Expose @SerializedName("so2") private String so2;

                @Override
                public String toString() {
                    return "City{" +
                            "aqi=" + aqi +
                            ", co=" + co +
                            ", no2=" + no2 +
                            ", o3=" + o3 +
                            ", pm10=" + pm10 +
                            ", pm25=" + pm25 +
                            ", qlty='" + qlty + '\'' +
                            ", so2=" + so2 +
                            '}';
                }

                public String getAqi() {
                    return aqi;
                }

                public void setAqi(String aqi) {
                    this.aqi = aqi;
                }

                public String getCo() {
                    return co;
                }

                public void setCo(String co) {
                    this.co = co;
                }

                public String getNo2() {
                    return no2;
                }

                public void setNo2(String no2) {
                    this.no2 = no2;
                }

                public String getO3() {
                    return o3;
                }

                public void setO3(String o3) {
                    this.o3 = o3;
                }

                public String getPm10() {
                    return pm10;
                }

                public void setPm10(String pm10) {
                    this.pm10 = pm10;
                }

                public String getPm25() {
                    return pm25;
                }

                public void setPm25(String pm25) {
                    this.pm25 = pm25;
                }

                public String getQlty() {
                    return qlty;
                }

                public void setQlty(String qlty) {
                    this.qlty = qlty;
                }

                public String getSo2() {
                    return so2;
                }

                public void setSo2(String so2) {
                    this.so2 = so2;
                }
            }
        }
    }
}
