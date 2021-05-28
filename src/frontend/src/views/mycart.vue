<template>
  <div>
    <loding v-if="isLoading" />
    <v-container>
      <v-card>
        <h1 align="center">
          <v-icon size="xxx-large" color="black">mdi-gift</v-icon>
          My Cart
        </h1>
<<<<<<< HEAD
        <v-card v-for="(cart, index) in carts" :key="cart.id">
=======
        <v-card v-for="(cart,index) in carts" :key="cart.id">
>>>>>>> 24f5937734df08af07719c8a063ab02b0a4959b9
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
<<<<<<< HEAD

=======
>>>>>>> 24f5937734df08af07719c8a063ab02b0a4959b9
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
import {mapActions} from 'vuex'

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
  created() {
    this.getMyCartList();
  },
  mounted() {
<<<<<<< HEAD
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
=======
  },
  methods: {
    ...mapActions({getMyCart: "carts/getMyCarts"}),
    ...mapActions({getMyDetail: "users/detail"}),
    async getMyCartList() {
      try {
        await this.getMyDetail();
        await this.getMyCart(this.$store.state.users.details.id);
        this.carts = this.$store.getters["carts/getMyCart"];
        this.isLoading = false;
      } catch {
        await this.$router.push({name: "Main"});
      }
    },
    async getUserDetails() {
      await this.getMyDetail();
    },
    cartDelete(idx, cartId) {
      console.log(cartId)
      this.carts.splice(idx, 1);
      return axios
          .delete("/api/carts/" + cartId)
          .then(res => {
            console.log(res)
          })
          .catch(err => {
            console.log(err);
          });
>>>>>>> 24f5937734df08af07719c8a063ab02b0a4959b9
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
