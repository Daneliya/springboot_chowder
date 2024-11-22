

官方文档：http://freemarker.foofun.cn/index.html

实战

https://blog.csdn.net/qq_45087487/article/details/129836101

https://blog.csdn.net/m0_51703800/article/details/126751939



问题1

ftl模板制作

https://blog.csdn.net/qq_38772928/article/details/134325133



问题2

~~~
FreeMarker template error:
The following has evaluated to null or missing:
==> user  [in template "Freemarker测试文档.ftl" at line 203, column 48]

----
Tip: If the failing expression is known to legally refer to something that's sometimes null or missing, either specify a default value like myOptionalVar!myDefault, or use <#if myOptionalVar??>when-present<#else>when-missing</#if>. (These only cover the last step of the expression; to cover the whole expression, use parenthesis: (myOptionalVar.foo)!myDefault, (myOptionalVar.foo)??
----

----
FTL stack trace ("~" means nesting-related):
	- Failed at: ${user.name}  [in template "Freemarker测试文档.ftl" at line 203, column 46]
----

Java stack trace (for programmers):
----
freemarker.core.InvalidReferenceException: [... Exception message was already printed; see it above ...]
~~~

https://blog.csdn.net/java_mr_zheng/article/details/50370943

~~~xml
<#if user??>
    <p>User object is not null</p>
    <p>User idNum: ${user.idNum!""}</p>
<#else>
    <p>User object is null</p>
</#if>
~~~

