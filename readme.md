## 版本信息 ##

v1.1.4

1．[BUG] FileUtil 修复获取文件名和后缀名方法参数非存在的文件或目录抛出异常
2．[ADDED] FileUtil 添加创建文件目录存在是否跳过控制
3．[ADDED] FileUtil 添加获取与系统文件分隔符统一的路径
4．[ADDED} ArrayUtil 添加数组列举的方法
4．[ADDED} StringUtil


v1.0.3

1．[ADDED] FileUtil 添加获取文件名和后缀名方法

v1.0.2

1．[BUG] 修复 Printer 打印对象列表不换行

2．[ADDED] TypeUtil 添加8种基本&装箱类型判断方法

## 依赖配置 ##

```xml
<repositories>
  <repository>
    <id>lychie-maven-repo</id>
    <url>https://raw.github.com/lychie/maven-repo/master/releases</url>
  </repository>
</repositories>

<dependencies>
  <dependency>
    <groupId>org.lychie</groupId>
    <artifactId>jutil</artifactId>
    <version>1.1.4</version>
  </dependency>
</dependencies>
```