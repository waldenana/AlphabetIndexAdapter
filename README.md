# PageListLoader
#使用方法
<img width="320" src="https://github.com/anzewei/AlphabetIndexAdapter/raw/master/screenshort/device-2016-04-02-093903.png"/>
<img width="320" src="https://github.com/anzewei/AlphabetIndexAdapter/raw/master/screenshort/device-2016-04-02-093940.png"/>
<img width="320" src="https://github.com/anzewei/AlphabetIndexAdapter/raw/master/screenshort/device-2016-04-02-093955.png"/>
##1,配置gradle

``` groovy
compile 'com.github.anzewei.alphabet:AlphabetIndexAdapter:0.1'
``` 
##2.继承AlphabetIndexAdapter
``` java
public class Friend implements AlphabetIndexAdapter.ILetterAble

public class FriendAdapter extends AlphabetIndexAdapter<Friend> {

	protected View getKeyView(int position, View convertView, ViewGroup parent) {
	    索引的view
	}
	
	protected View getChildView(Friend item, View convertView, ViewGroup parent) {
	    子项的view
	}
}

``` 
##3.其他设置
通过调用setShowKey显示或隐藏索引view

``` java
adapter.setShowKey(false);
```
