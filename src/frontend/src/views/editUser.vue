<template>
  <div id="signUp">
    <v-card width="400" class="mx-auto mt-5">
      <v-card-title style="background-color: grey; margin-bottom:5px;">
        <h3 style="color: white">
          Edit User
        </h3>
      </v-card-title>
      <v-card-text>
        <div id="signUpForm">
          <v-form>
            <v-container>
              <v-row>
                <v-col>
                  <v-text-field
                      v-model="email"
                      :rules="[rules.required]"
                      label="Email address"
                      prepend-icon="mdi-account-circle"
                      readonly
                      required
                  ></v-text-field>
                </v-col>
              </v-row>

              <v-row>
                <v-col>
                  <v-text-field
                      v-model="password"
                      :append-icon="show1 ? 'mdi-eye' : 'mdi-eye-off'"
                      :rules="[rules.required, rules.minPw]"
                      :type="show1 ? 'text' : 'password'"
                      name="input-10-1"
                      label="New Password"
                      hint="At least 8 characters"
                      prepend-icon="mdi-lock"
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
                      prepend-icon="mdi-lock"
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
                      label="Edit Nickname"
                      prepend-icon="mdi-account"
                      required
                  ></v-text-field>
                </v-col>
              </v-row>
            </v-container>
          </v-form>
        </div>
      </v-card-text>
      <v-divider></v-divider>
      <v-card-actions>
        <v-btn
            :disabled="!valid"
            color="blue-grey darken-3"
            class="mr-0 white--text"
            @click="update"
        >
          Edit
          <v-icon right>mdi-arrow-right-thick</v-icon>
        </v-btn>

        <v-btn color="blue-grey darken-3" class="mr-0 white--text">
          Cancel
          <v-icon right>mdi-cancel</v-icon>
        </v-btn>
        <v-btn color="error" class="mr-1 white--text" @click="deleteUser">
          Delete account
          <v-icon right>mdi-trash-can</v-icon>
        </v-btn>
      </v-card-actions>
    </v-card>
  </div>
</template>
<script>
import {mapActions} from 'vuex'

export default {
  data() {
    return {
      show1: false,
      show2: false,
      email: this.$store.state.users.details.email,
      password: "",
      passwordCheck: "",
      nickname: "",
      valid: true,

      rules: {
        required: input => !!input || "Required.",
        minPw: password => password.length >= 8 || "Min 8 characters",
        minName: nickname => nickname.length >= 2 || "Min 2 characters",
        pwCheck: passwordCheck =>
            this.password === passwordCheck || "Password mismatch"
      }
    };
  },
  created() {
    this.getUserDetails();
  },
  methods: {
    ...mapActions({updateUser: 'users/update'}),
    ...mapActions({delUser: 'users/deleteUser'}),
    ...mapActions({logout: 'users/logout'}),
    ...mapActions({getUser: 'users/details'}),
    update: async function () {
      const userUpdateDto = {
        id: this.$store.state.users.details.id,
        email: this.email,
        password: this.password,
        passwordCheck: this.passwordCheck,
        nickname: this.nickname
      };
      if (await this.updateUser(userUpdateDto)) {
        await this.$router.push({name: "Login"})
      }
    },
    deleteUser: async function () {
      if (await this.delUser()) {
        await this.$router.push({name: "Main"});
      }
    },
    logoutUser: function () {
      this.logout();
    },
    getUserDetails: function () {
      this.getUser(this.$store.state.users.id);
    }
  }
};
</script>
<style scoped></style>