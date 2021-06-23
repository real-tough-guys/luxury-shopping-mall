<template>
  <div>
    <v-container>
      <v-row no-gutters>
        <v-col cols="12" sm="6" align="right">
          <v-card outlined tile class="pa-2" max-width="400" max-height="600">
            <v-carousel progress-color="orange">
              <v-carousel-item
                v-for="(item, i) in productDetail.productImageurl"
                :key="i"
                v-bind:src="item | loadImgOrPlaceholder"
              ></v-carousel-item>
            </v-carousel>
          </v-card>
        </v-col>
        <v-col cols="12" sm="6">
          <v-card class="pa-2" outlined tile max-width="400" max-height="600">
            <v-card-title
              ><h4>{{ productDetail.productName }}</h4>
              <v-btn class="mx-2" fab dark small color="pink">
                <v-icon dark>
                  mdi-heart
                </v-icon>
              </v-btn>
            </v-card-title>
            <v-card-text>
              {{
                `가격 : ${productDetail.productPrice} 원 ` | moneyFilter
              }}</v-card-text
            >

            <v-btn
              color="blue-grey"
              class="ma-2 white--text"
              @click="postCartAdd(productDetail.id)"
            >
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
              color :
              <v-select
                :items="productDetail.productColor"
                v-model="colorSelect"
                label="Standard"
              ></v-select>
            </v-card-actions>
            <v-card-actions>
              size :
              <v-select
                :items="productDetail.productSize"
                v-model="sizeSelect"
                label="Standard"
              ></v-select>
            </v-card-actions>
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
                  >Next slide
                </v-btn>
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
import axios from "axios";
import myMixin from "@/filter";

export default {
  mixins: [myMixin],
  props: ["id"],
  data() {
    return {
      productDetail: [],
      colorSelect: "",
      sizeSelect: "",
      colors: [
        "pink darken-2",
        "pink darken-2",
        "pink darken-2",
        "pink darken-2",
        "pink darken-2"
      ],
      slides: ["First", "Second", "Third", "Fourth", "Fifth"]
    };
  },
  filters: {
    loadImgOrPlaceholder: function(path) {
      return require("@/assets/images/" + path);
    }
  },
  computed: {},
  mounted() {
    this.init();
  },
  methods: {
    init() {
      this.getProduct(this.id);
    },
    getProduct(id) {
      return axios
        .get("/api/products/detail/" + id)
        .then(res => {
          this.productDetail = res.data;
          this.isLoading = false;
        })
        .catch(err => {
          console.log(err);
        });
    },
    postCartAdd(id) {
      return axios
        .post(
          "/api/carts/",
          {
            productId: id,
            userId: this.$store.state.users.details.id,
            color: this.colorSelect,
            size: this.sizeSelect
          },
          {
            headers: { Authorization: `Bearer ${this.$store.state.users.jwt}` }
          }
        )
        .then(res => {
          this.productDetail = res.data;
          this.$router.push({ name: "Cart" });
        })
        .catch(err => {
          console.log(err);
        });
    }
  }
};
</script>
<style></style>
