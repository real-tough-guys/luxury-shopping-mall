<template>
  <div>
    <v-container class="grey lighten-5">
      <v-row>
        <v-col cols="12" sm="6">
          <v-card class="pa-2" outlined tile>
            <v-carousel progress-color="orange">
              <v-carousel-item
                v-for="(item, i) in items"
                :key="i"
                :src="item.src"
                reverse-transition="fade-transition"
                transition="fade-transition"
              ></v-carousel-item>
            </v-carousel>
          </v-card>
        </v-col>

        <v-col cols="12" sm="6">
          <v-card class="pa-2" outlined tile>
            <v-card-title
              ><h4>{{ detailName }}</h4>
              <v-btn class="mx-2" fab dark small color="pink">
                <v-icon dark>
                  mdi-heart
                </v-icon>
              </v-btn>
            </v-card-title>
            <v-card-text>{{ `가격 : ${price} 원 ` }}</v-card-text>

            <v-btn color="blue-grey" class="ma-2 white--text">
              Add To Cart
              <v-icon right dark>
                mdi-cart
              </v-icon>
            </v-btn>

            <v-btn color="blue-grey" class="ma-2 white--text">
              Buy It Now
              <v-icon right dark>
                mdi-arrow-right-bold
              </v-icon>
            </v-btn>
            <v-card-actions>
              color : <v-select :items="items2" label="Standard"> </v-select
            ></v-card-actions>
            <v-card-actions>
              size : <v-select :items="items3" label="Standard"> </v-select
            ></v-card-actions>
            <v-card-title>REALATED ITEM</v-card-title>
            <v-carousel
              cycle
              height="150"
              hide-delimiter-background
              show-arrows-on-hover
            >
              <template v-slot:prev="{ on, attrs }">
                <v-btn color="blue-grey" v-bind="attrs" v-on="on"
                  >Previous slide
                </v-btn>
              </template>
              <template v-slot:next="{ on, attrs }">
                <v-btn color="blue-grey" v-bind="attrs" v-on="on"
                  >Next slide</v-btn
                >
              </template>
              <v-carousel-item v-for="(slide, i) in slides" :key="i">
                <v-sheet :color="colors[i]" height="100%">
                  <v-row class="fill-height" align="center" justify="center">
                    <div class="display-3">{{ slide }} Slide</div>
                  </v-row>
                </v-sheet>
              </v-carousel-item>
            </v-carousel>
          </v-card>
        </v-col>
      </v-row>
    </v-container>
  </div>
</template>

<script>
export default {
  data() {
    return {
      items: [
        {
          src:
            "https://image.musinsa.com/images/prd_img/2021021921333500000079112.jpg"
        },
        {
          src:
            "https://image.musinsa.com/images/prd_img/2021021921333500000011944.jpg"
        },
        {
          src:
            "https://image.musinsa.com/images/prd_img/2021021921333600000035549.jpg"
        },
        {
          src:
            "https://image.musinsa.com/images/prd_img/2021021921333600000098070.jpg"
        }
      ],
      products: [
        {
          id: 0,
          title: "미니멀 자켓",
          price: 70900
        }
      ],
      detailName: "미니멀 자켓",
      price: 70900,
      colors: [
        "pink darken-2",
        "pink darken-2",
        "pink darken-2",
        "pink darken-2",
        "pink darken-2"
      ],
      items2: ["black", "white"],
      items3: ["free"],
      slides: ["First", "Second", "Third", "Fourth", "Fifth"]
    };
  }
};
</script>
<style></style>
