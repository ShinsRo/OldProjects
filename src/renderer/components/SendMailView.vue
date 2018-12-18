<template>
  <v-layout row wrap>
    <v-flex xs12 class='mb-3'>
      <h1 class="headline">메일 보내기</h1>
      <small>에러 로그는 자동으로 첨부됩니다.</small>
      <v-divider></v-divider>
    </v-flex>
    <v-flex xs3 mr-3>
      <v-text-field
        label="답장받으실 이메일 주소"
        box
        v-model="from"
      ></v-text-field>
    </v-flex>
    <v-flex>
      <v-text-field
        label="제목"
        box
        v-model="subject"
      ></v-text-field>
    </v-flex>
    <v-flex xs12>
      <v-textarea xs12
        box
        name="mail_content"
        label="보내실 내용"
        v-model="mail_content"
      ></v-textarea>
    </v-flex>
    <v-flex xs12 class="text-xs-right">
      <v-btn @click="sendMail">메일 보내기</v-btn>
    </v-flex>
    <!-- 로딩 -->
    <v-dialog
      v-model="sending"
      hide-overlay
      persistent
      width="300"
    >
      <v-card
        color="grey"
        dark
      >
        <v-card-text>
          이메일을 전송 중입니다.
          <v-progress-linear
            indeterminate
            color="white"
            class="mb-0"
          ></v-progress-linear>
        </v-card-text>
      </v-card>
    </v-dialog>
  </v-layout>
</template>
<script>
const nodemailer = require('nodemailer');
const smtpPool = require('nodemailer-smtp-pool');

export default {
  data() {
    return {
      sending: false,
      from: '',
      mail_content: '',
      subject: '',
      config: {
        service: 'Gmail',
        host: 'localhost',
        port: 465,
        user: 'sju.sandan.seungshin@gmail.com',
        password: 'kim34083780',
        // host: 'smtp.ethereal.email',
        // port: 587,
        // user: 'sju.sandan.seungshin@gmail.com',
        // password: 'b5DNNZqDxwRbvBND7g',
      },
    };
  },
  methods: {
    sendMail() {
      this.sending = true;
      const config = this.config;
      const transporter = nodemailer.createTransport(smtpPool({
        service: config.service,
        host: config.host,
        port: config.port,
        secure: false,
        auth: {
          user: config.user,
          pass: config.password,
        },
        tls: {
          rejectUnauthorize: false,
        },
        maxConnections: 5,
        maxMessages: 10,
      }));
      const mailOptions = {
        from: `"sejong-wos" <${this.from}>`,
        to: '"산학협력단&연구처" <sandan@sejong.ac.kr>',
        subject: `✔ ${this.subject} <${this.from}>`,
        text: this.mail_content,
        attachments: [
          {
            path: './.sjulog',
          },
        ],
      };
      transporter.sendMail(mailOptions, (err, info) => {
        if (err) {
          alert('알 수 없는 오류로 전송에 실패했습니다.');
          this.sending = false;
          return alert(err);
        }
        this.sending = false;
        alert('진행로그를 포함한 이메일을 성공적으로 발송했습니다.');
        return info;
      });
    },
  },
};
</script>

<style scoped>
  img {
    margin-left: auto;
    margin-right: auto;
    display: block;
  }
</style>