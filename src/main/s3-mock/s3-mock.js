var express = require('express');
var app = express();

const S3rver = require('s3rver');
const { fromEvent } = require('rxjs');
const { filter } = require('rxjs/operators');

const instance = new S3rver({
  port: 4568,
  hostname: 'localhost',
  silent: false,
  directory: '/tmp/s3rver_test_directory',
  configureBuckets: [
    {
      name: 'objective-informa-arquivos',
      configs: [ '<CORSConfiguration>\n' +
      '    <CORSRule>\n' +
      '        <AllowedOrigin>*</AllowedOrigin>\n' +
      '        <AllowedMethod>GET</AllowedMethod>\n' +
      '        <AllowedMethod>PUT</AllowedMethod>\n' +
      '        <AllowedMethod>POST</AllowedMethod>\n' +
      '        <MaxAgeSeconds>3000</MaxAgeSeconds>\n' +
      '        <AllowedHeader>*</AllowedHeader>\n' +
      '    </CORSRule>\n' +
      '</CORSConfiguration>']
    }
  ]
}).run((err, { address, port } = {}) => {
  if (err) {
    console.error(err);
  } else {
    console.log('now listening at address %s and port %d', address, port);
  }
});
