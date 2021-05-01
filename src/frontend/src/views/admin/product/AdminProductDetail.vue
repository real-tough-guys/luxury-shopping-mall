<template>
  <div>
    <loding v-if="isLoading"/>
    <v-card>
      <v-toolbar color="primary" dark flat prominent>
        <h1>Admin Detail</h1>
      </v-toolbar>
      <v-container class="grey lighten-5">
        <v-row>
          <v-col cols="12" sm="6">
            <v-card class="pa-2" outlined tile>
              <v-carousel progress-color="orange">
                <v-carousel-item v-for="(item, i) in productDetail.productImageurl" :key="i"
                                 v-bind:src="item |loadImgOrPlaceholder" width="344" height="auto"
                                 reverse-transition="fade-transition" transition="fade-transition"></v-carousel-item>
              </v-carousel>
            </v-card>
          </v-col>
          <v-col cols="12" sm="6">
            <v-card class="pa-2" outlined tile>
              <v-card-title
              ><h4>{{ productDetail.productName }}</h4>
              </v-card-title>
              <v-card-text>{{
                  `가격 : ${productDetail.productPrice} 원 `
                }}
              </v-card-text>
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

export default {
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
  filters: {
    loadImgOrPlaceholder: function (path) {
      return require("@/assets/images/" + path)
    }
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
