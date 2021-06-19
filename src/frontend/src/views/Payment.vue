<template>
  <div>
    <v-container>
      <v-card>
        <v-card-title><h4>주문상품 정보</h4></v-card-title>
        <v-card v-for="item in order">
          <v-layout>
            <v-flex xs3>
              <v-img
                v-bind:src="
                  item.product.productImageurl[0] | loadImgOrPlaceholder
                "
                contain
                height="125px"
              ></v-img>
            </v-flex>
            <v-layout column>
              <v-card-title>{{ item.product.productName }}</v-card-title>
              <v-card-text
                >{{ `가격 : ${item.product.productPrice} 원 ` | moneyFilter }}
              </v-card-text>
              <v-card-subtitle
                >색상 : {{ item.color }} || 사이즈 :
                {{ item.size }}
              </v-card-subtitle>
            </v-layout>
          </v-layout>
        </v-card>

        <v-card-title
          >주문금액 (KRW {{ total | moneyFilter }} 원)
        </v-card-title>
      </v-card>

      <v-card>
        <v-card-title><h4>주문자 정보</h4></v-card-title>
        <v-text-field
          v-model="order[0].user.nickname"
          color="primary"
          outlined
          label="받는 분 이름"
        >
        </v-text-field>
        <v-text-field
          v-model="order[0].user.email"
          color="primary"
          outlined
          label="받는 분 이메일"
        ></v-text-field>
      </v-card>
      <v-card>
        <v-card-title><h4>배송 정보</h4></v-card-title>
        <v-text-field
          v-model="payAddress"
          color="primary"
          outlined
          label="주소지"
        ></v-text-field>
        <v-card-title><h4>연락처</h4></v-card-title>
        <v-text-field
          v-model="payPhoneNumber"
          color="primary"
          outlined
          label="휴대폰 번호"
        ></v-text-field>
        <v-text-field
          v-model="deliveryMessage"
          color="primary"
          outlined
          label="배송 메시지를 입력해주세요"
        ></v-text-field>
        <v-btn rounded color="red" class="ma-2 white--text" @click="allSubmit">
          결제하기
        </v-btn>
      </v-card>

      <v-spacer />
    </v-container>
  </div>
</template>
<script>
import myMixin from "@/filter";
import axios from "axios";

export default {
  mixins: [myMixin],
  props: ["order"],
  data() {
    return {
      orderProducts: null,
      checkbox: true,
      payAddress: null,
      payPhoneNumber: "010-1111-1111",
      deliveryMessage: "",
      impCode: "imp38067213",
      payCheck: false
    };
  },
  mounted() {
    this.orderProducts = this.order;
    this.payAddress = this.orderProducts[0].user.address;
  },
  methods: {
    allSubmit() {
      this.requestPay();
    },
    requestPay() {
      var IMP = window.IMP;
      IMP.init(this.impCode);
      //3. 결제창 호출
      IMP.request_pay(
        {
          pg: "html5_inicis",
          pay_method: "card",
          merchant_uid: "merchant_" + new Date().getTime(),
          name: this.orderProducts[0].product.productName,
          amount: 100,
          buyer_tel: this.order.buyer_tel
        },
        rsp => {
          if (rsp.success) {
            var msg = "결제가 완료되었습니다.";
            msg += "고유ID : " + rsp.imp_uid;
            msg += "상점 거래ID : " + rsp.merchant_uid;
            msg += "결제 금액 : " + rsp.paid_amount;
            msg += "카드 승인번호 : " + rsp.apply_num;
            alert(msg);
            this.orderStatementCreate();
          } else {
            var msg = "결제에 실패하였습니다.";
            msg += "에러내용 : " + rsp.error_msg;
            alert(msg);
          }
        }
      );
    },
    orderStatementCreate() {
      console.log("에이시오스 호출");
      const cartId = [];
      const size = [];
      const color = [];
      for (var i = 0; i < this.orderProducts.length; i++) {
        cartId.push(this.orderProducts[i].id);
        color.push(this.orderProducts[i].color);
        size.push(this.orderProducts[i].size);
      }
      axios
        .post("/api/orders", {
          userId: this.$store.state.users.id,
          cartId: cartId,
          totalMoney: this.totalMoney,
          myAddress: this.payAddress,
          deliveryMessage: this.deliveryMessage
        })
        .then(res => {
          console.log(res);
          this.$router.push({ name: "Mypage" });
        })
        .catch(err => {
          console.log(err);
        });
    }
  },
  computed: {
    total() {
      let total = 0;
      this.order.forEach(orderItem => {
        total += orderItem.product.productPrice;
      });
      this.totalMoney = total;
      return total;
    }
  }
};
</script>
