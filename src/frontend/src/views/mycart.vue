<template>
  <div>
    <loding v-if="isLoading" />
    <v-container>
      <v-card>
        <h1 align="center">
          <v-icon size="xxx-large" color="black">mdi-gift</v-icon>
          My Cart
        </h1>
        <v-card v-for="(cart, index) in carts" :key="cart.id">
          <v-layout>
            <v-flex xs3>
              <v-img
                v-bind:src="
                  cart.product.productImageurl[0] | loadImgOrPlaceholder
                "
                contain
                height="125px"
              ></v-img>
            </v-flex>
            <v-layout column>
              <v-card-title
                ><h4>{{ cart.product.productName }}</h4></v-card-title
              >
              <v-card-text>{{
                `가격 : ${cart.product.productPrice} 원 ` | moneyFilter
              }}</v-card-text>
            </v-layout>
            <v-card-actions>
              <v-btn
                right
                color="blue-grey"
                class="ma-2 white--text"
                fab
                right
                @click="cartDelete(index, cart.id)"
              >
                <v-icon dark>
                  mdi-delete
                </v-icon>
              </v-btn>
            </v-card-actions>
          </v-layout>
        </v-card>
        <v-card-subtitle>
          <h3 align="center">
            수량

            <p style="color: orange">{{ carts.length }}</p>
            Total Price($ {{ total | moneyFilter }} 원)
          </h3>
        </v-card-subtitle>
      </v-card>
      <v-spacer />
    </v-container>
  </div>
</template>

<script>
import axios from "axios";
import Loding from "@/components/Loding.vue";
import myMixin from "@/filter";

export default {
  mixins: [myMixin],
  data() {
    return {
      isLoading: true,
      carts: []
    };
  },
  components: {
    Loding
  },
  mounted() {
    this.getCart();
  },
  methods: {
    cartDelete(idx, cartId) {
      console.log(cartId);
      this.carts.splice(idx, 1);
      return axios
        .delete("/api/carts/" + cartId)
        .then(res => {
          console.log(res);
        })
        .catch(err => {
          console.log(err);
        });
    },
    getCart() {
      return axios
        .get("/api/carts/")
        .then(res => {
          console.log(res.data);
          this.carts = res.data;
          this.isLoading = false;
        })
        .catch(err => {
          console.log(err);
        });
    }
  },
  computed: {
    total() {
      let total = 0;
      this.carts.forEach(cartItem => {
        total += cartItem.product.productPrice;
      });
      return total;
    }
  }
};
</script>
