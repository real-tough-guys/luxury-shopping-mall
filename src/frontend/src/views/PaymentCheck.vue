<template>
  <div>
    <v-container>
      {{orderCheck}}
<!--      <v-card>-->
<!--        <h1 align="center">-->
<!--          <v-icon size="xxx-large" color="black">mdi-gift</v-icon>-->
<!--          My Cart-->
<!--        </h1>-->
<!--        <v-card v-for="(cart, index) in carts" :key="cart.id">-->
<!--          <v-layout>-->
<!--            <v-flex xs3>-->
<!--              <v-img-->
<!--                v-bind:src="-->
<!--                  cart.product.productImageurl[0] | loadImgOrPlaceholder-->
<!--                "-->
<!--                contain-->
<!--                height="125px"-->
<!--              ></v-img>-->
<!--            </v-flex>-->
<!--            <v-layout column>-->
<!--              <v-card-title-->
<!--                ><h4>{{ cart.product.productName }}</h4></v-card-title-->
<!--              >-->
<!--              <v-card-subtitle-->
<!--                >색상 : {{ cart.color }} || 사이즈 :-->
<!--                {{ cart.size }}</v-card-subtitle-->
<!--              >-->
<!--              <v-card-text>{{-->
<!--                `가격 : ${cart.product.productPrice} 원 ` | moneyFilter-->
<!--              }}</v-card-text>-->
<!--            </v-layout>-->
<!--            <v-card-actions>-->
<!--              <v-btn-->
<!--                color="blue-grey"-->
<!--                class="ma-2 white&#45;&#45;text"-->
<!--                fab-->
<!--                right-->
<!--                @click="cartDelete(index, cart.id)"-->
<!--              >-->
<!--                <v-icon dark>-->
<!--                  mdi-delete-->
<!--                </v-icon>-->
<!--              </v-btn>-->
<!--            </v-card-actions>-->
<!--          </v-layout>-->
<!--        </v-card>-->
<!--        <v-card-subtitle>-->
<!--          <h3 align="center">-->
<!--            수량-->
<!--            <p style="color: orange">{{ carts.length }}</p>-->
<!--            Total Price($ {{ total | moneyFilter }} 원)-->
<!--            <v-btn-->
<!--              rounded-->
<!--              color="blue-grey"-->
<!--              class="ma-2 white&#45;&#45;text"-->
<!--              @click="orderPayment"-->
<!--            >-->
<!--              주문하기-->
<!--            </v-btn>-->
<!--          </h3>-->
<!--        </v-card-subtitle>-->
<!--      </v-card>-->
      <v-spacer />
    </v-container>
  </div>
</template>

<script>
import axios from "axios";
import myMixin from "@/filter";
export default {
  mixins: [myMixin],
  props: ["id","userId"],
  data() {
    return {
      orderCheck: []
    };
  },
  mounted() {
    this.getOrderCheck();
  },
  methods: {
    getOrderCheck() {
      console.log(this.userId)
      return axios
        .get("/api/orders/detail" + this.id,{params: { userId: this.userId }})
        .then(res => {
          this.orderCheck = this.res
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
