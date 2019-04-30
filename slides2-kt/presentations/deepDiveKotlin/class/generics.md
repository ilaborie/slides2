* ⚠️ Les contrôles de types génériques ne sont fait qu'au moment de la compilation
* Covariant: `out`, en java `? extends T`
* Contravariant: `in`, en java `? super T`


<div>
    <h4>Borne supérieure</h4>
    <pre class="lang-kotlin"><code><span class="token keyword">fun</span> <span class="token operator">&lt;</span>T <span class="token operator">:</span> Comparable<span class="token operator">&lt;</span>T<span class="token operator">&gt;</span><span class="token operator">&gt;</span> <span class="token function">sort</span><span class="token punctuation">(</span>list<span class="token operator">:</span> List<span class="token operator">&lt;</span>T<span class="token operator">&gt;</span><span class="token punctuation">)</span><span class="token operator">:</span> List<span class="token operator">&lt;</span>T<span class="token operator">&gt;</span></code></pre>
</div>


Les détails: <https://kotlinlang.org/docs/reference/generics.html>
