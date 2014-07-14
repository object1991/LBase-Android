package com.leo.base.entity;

import java.util.List;
import java.util.Map;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 
 * @author Chen Lei
 *
 */
@SuppressWarnings("unchecked")
public class LMessage implements Parcelable {

	private int what;

	private int arg1;

	private int arg2;

	private String str;

	private Object obj;

	private List<?> list;

	private Map<?, ?> map;

	public int getWhat() {
		return what;
	}

	public void setWhat(int what) {
		this.what = what;
	}

	public int getArg1() {
		return arg1;
	}

	public void setArg1(int arg1) {
		this.arg1 = arg1;
	}

	public int getArg2() {
		return arg2;
	}

	public void setArg2(int arg2) {
		this.arg2 = arg2;
	}

	public String getStr() {
		return str;
	}

	public void setStr(String str) {
		this.str = str;
	}

	public Object getObj() {
		return obj;
	}

	public void setObj(Object obj) {
		this.obj = obj;
	}

	public <T> List<T> getList() {
		return (List<T>) list;
	}

	public <T> void setList(List<T> list) {
		this.list = list;
	}

	public <K, V> Map<K, V> getMap() {
		return (Map<K, V>) map;
	}

	public <K, V> void setMap(Map<K, V> map) {
		this.map = map;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeInt(what);
		dest.writeInt(arg1);
		dest.writeInt(arg2);
		if (null != str) {
			dest.writeInt(1);
			dest.writeString(str);
		} else {
			dest.writeInt(-1);
		}
		if (obj != null) {
			try {
				Parcelable pObj = (Parcelable) obj;
				dest.writeInt(2);
				dest.writeParcelable(pObj, flags);
			} catch (ClassCastException e) {
				throw new RuntimeException(
						"Can't marshal non-Parcelable objects across processes.");
			}
		} else {
			dest.writeInt(-2);
		}
		if (list != null) {
			dest.writeInt(3);
			dest.writeList(list);
		} else {
			dest.writeInt(-3);
		}
		if (map != null) {
			dest.writeInt(4);
			dest.writeMap(map);
		} else {
			dest.writeInt(-4);
		}
	}

	public static final Parcelable.Creator<LMessage> CREATOR = new Creator<LMessage>() {

		@Override
		public LMessage createFromParcel(Parcel source) {
			LMessage msg = new LMessage();
			msg.what = source.readInt();
			msg.arg1 = source.readInt();
			msg.arg2 = source.readInt();
			if (source.readInt() == 1) {
				msg.str = source.readString();
			}
			if (source.readInt() == 2) {
				msg.obj = source.readParcelable(getClass().getClassLoader());
			}
			if (source.readInt() == 3) {
				msg.list = source.readArrayList(getClass().getClassLoader());
			}
			if (source.readInt() == 4) {
				msg.map = source.readHashMap(getClass().getClassLoader());
			}
			return msg;
		}

		@Override
		public LMessage[] newArray(int size) {
			return new LMessage[size];
		}

	};

}
