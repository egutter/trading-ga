#!/usr/bin/env ruby

require 'webrick'
require 'webrick/https'

cert_name = [
  %w[CN localhost],
]

server = WEBrick::HTTPServer.new(
    :Port => 8443,
    :SSLEnable => true,
    :SSLCertName => cert_name
)

server.mount_proc '/' do |req, res|
  puts "req #{req}"
  res.body = 'Example Domain Encrypted'
end

server.start
