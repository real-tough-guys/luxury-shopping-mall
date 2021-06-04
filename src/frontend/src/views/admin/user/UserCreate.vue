<template>
  <div>
    <h1>
      Admin Sign Up
    </h1>
    <div id="signUpForm">
      <v-form>
        <v-container>
          <v-row>
            <v-col>
              <v-text-field
                  v-model="email"
                  :rules="[rules.required]"
                  label="Email address"
                  required
              ></v-text-field>
            </v-col>
            <v-btn
                :disabled="!email"
                id="checkEmail"
                elevation="1"
                color="primary"
                @click="isDuplicatedEmail"
            >
              check
            </v-btn>
          </v-row>

          <v-row>
            <v-col>
              <v-text-field
                  v-model="password"
                  :append-icon="show1 ? 'mdi-eye' : 'mdi-eye-off'"
                  :rules="[rules.required, rules.minPw]"
                  :type="show1 ? 'text' : 'password'"
                  name="input-10-1"
                  label="Password"
                  hint="At least 8 characters"
                  counter
                  @click:append="show1 = !show1"
              ></v-text-field>
            </v-col>
          </v-row>

          <v-row>
            <v-col>
              <v-text-field
                  v-model="passwordCheck"
                  :append-icon="show2 ? 'mdi-eye' : 'mdi-eye-off'"
                  :rules="[rules.required, rules.minPw, rules.pwCheck]"
                  :type="show2 ? 'text' : 'password'"
                  name="input-10-1"
                  label="Password Check"
                  hint="Enter your password once more."
                  counter
                  @click:append="show2 = !show2"
              ></v-text-field>
            </v-col>
          </v-row>

          <v-row>
            <v-col>
              <v-text-field
                  v-model="nickname"
                  :rules="[rules.minName]"
                  label="Nickname"
                  required
              ></v-text-field>
            </v-col>
          </v-row>

          <v-row align-self>

            <v-btn color="primary" @click="signUp">
              Sign Up
            </v-btn>


          </v-row>

        </v-container>
      </v-form>
    </div>
  </div>
</template>
<script>
import {mapActions} from "vuex";

export default {
  data() {
    return {
      show1: false,
      show2: false,
      email: "",
      password: "",
      passwordCheck: "",
      nickname: "",

      rules: {
        required: input => !!input || "Required.",
        minPw: password => password.length >= 8 || "Min 8 characters",
        minName: nickName => nickName.length >= 6 || "Min 6 characters",
        pwCheck: passwordCheck =>
            this.password === passwordCheck || "Password mismatch"
      }
    };
  },
  methods: {
    ...mapActions({signup: 'users/signUp'}),
    ...mapActions({duplicateEmail: 'users/isDuplicatedEmail'}),
    ...mapActions({getUserList: "users/findAll"}),
    isDuplicatedEmail: function () {
      this.duplicateEmail(this.email);
    },
    async signUp() {
      const userSignupDto = {
        email: this.email,
        password: this.password,
        passwordCheck: this.passwordCheck,
        nickname: this.nickname
      };
      if (await this.signup(userSignupDto)) {
        await this.getUserList();
        this.UserList = this.$store.state.users.userList;
      }
    },
  }
};
</script>
<style scoped>
div#signUp {
  width: 510px;
  margin: auto;
}

h1 {
  margin-top: 50px;
  margin-bottom: 30px;
}

div#signUpButton {
  width: 210px;
  margin: auto;
}

div.signUpBtn {
  text-align: center;
  margin: auto;
}
</style>