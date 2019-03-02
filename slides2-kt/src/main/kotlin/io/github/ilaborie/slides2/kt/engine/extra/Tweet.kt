package io.github.ilaborie.slides2.kt.engine.extra


//TODO

//{{- $json := getJSON "https://api.twitter.com/1/statuses/oembed.json?id=" $id "&omit_script=true" -}}
//{{- if not $sc.DisableInlineCSS -}}
//{{ template "__h_simple_twitter_css" $ }}
//{{- end -}}
//{{ $json.html | safeHTML }}