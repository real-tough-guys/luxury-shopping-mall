<template>
  <div>
    <loding v-if="isLoading"/>
    <v-card>
      <v-toolbar color="primary" dark flat prominent>
        <h1>Admin Detail</h1>
      </v-toolbar>

      <v-container >
        <v-row class="mb-6 " no-gutters  >
          <v-col sm="5" md="6" align="right" justify="center">
            <v-card class="pa-2" outlined tile width="344" >
              <v-carousel hide-delimiters>
                <v-carousel-item
                    v-for="(item, i) in productDetail.productImageurl" :key="i"
                    v-bind:src="item |loadImgOrPlaceholder"
                ></v-carousel-item>
              </v-carousel>
            </v-card>
          </v-col>
          <v-col sm="5" offset-sm="2" md="3" offset-md="0">

            <v-card class="pa-2" outlined tile width="344">
              <v-card-title
              ><h3>Name : {{ productDetail.productName }}</h3>
              </v-card-title>
              <v-card-title
              ><h3>Content : {{ productDetail.productContent }}</h3>
              </v-card-title>
              <v-card-text>{{ `가격 : ${productDetail.productPrice} won ` | moneyFilter}}
              </v-card-text>
              <v-card-actions>
                color :
                <v-select :items="productDetail.productColor" label="Standard"></v-select>
              </v-card-actions>
              <v-card-actions>
                size :
                <v-select :items="productDetail.productSize" label="Standard"></v-select>
              </v-card-actions>
              <v-btn color="blue-grey" class="ma-2 white--text" @click="updatePush">
                UPDate
                <v-icon right dark>
                  mdi-pencil
                </v-icon>
              </v-btn>
              <v-btn color="blue-grey" class="ma-2 white--text" @click="deleteProduct">
                DELETE
                <v-icon right dark>
                  mdi-delete
                </v-icon>
              </v-btn>
            </v-card>
          </v-col>
        </v-row>

      </v-container>


    </v-card>
  </div>
</template>
<script>
import axios from "axios";
import Loding from "@/components/Loding.vue";
import myMixin from "@/filter";

export default {
  mixins: [myMixin],
  props: ["id"],
  components: {
    Loding,
  },
  data() {
    return {
      isLoading: true,
      productDetail: [],
    };
  },
  computed: {},
  mounted() {
    this.init();
  },
  methods: {
    init() {
      this.getProduct(this.id)
    },
    getProduct(id) {
      return axios.get("/api/products/detail/" + id)
          .then(res => {
            this.productDetail = res.data
            this.isLoading = false;
          })
          .catch(err => {
            console.log(err);
          });
    },
    updatePush() {
      this.$router.push({name: "AdminProductUpdate", params: {id: this.id}});
    },
    deleteProduct() {
      if (confirm("삭제 하시겠습니까?")) {
        return axios.delete("/api/products/" + this.id)
            .then(res => {
              this.$router.push({name: "AdminPage"});
            })
            .catch(err => {
              console.log(err);
            });
      }
    }

  }
};
</script>
