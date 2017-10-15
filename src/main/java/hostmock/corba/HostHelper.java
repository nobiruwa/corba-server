package hostmock.corba;


/**
* hostmock/corba/HostHelper.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from idl/Host.idl
* Sunday, October 15, 2017 2:21:43 PM UTC
*/

abstract public class HostHelper
{
  private static String  _id = "IDL:hostmock/corba/Host:1.0";

  public static void insert (org.omg.CORBA.Any a, hostmock.corba.Host that)
  {
    org.omg.CORBA.portable.OutputStream out = a.create_output_stream ();
    a.type (type ());
    write (out, that);
    a.read_value (out.create_input_stream (), type ());
  }

  public static hostmock.corba.Host extract (org.omg.CORBA.Any a)
  {
    return read (a.create_input_stream ());
  }

  private static org.omg.CORBA.TypeCode __typeCode = null;
  synchronized public static org.omg.CORBA.TypeCode type ()
  {
    if (__typeCode == null)
    {
      __typeCode = org.omg.CORBA.ORB.init ().create_interface_tc (hostmock.corba.HostHelper.id (), "Host");
    }
    return __typeCode;
  }

  public static String id ()
  {
    return _id;
  }

  public static hostmock.corba.Host read (org.omg.CORBA.portable.InputStream istream)
  {
    return narrow (istream.read_Object (_HostStub.class));
  }

  public static void write (org.omg.CORBA.portable.OutputStream ostream, hostmock.corba.Host value)
  {
    ostream.write_Object ((org.omg.CORBA.Object) value);
  }

  public static hostmock.corba.Host narrow (org.omg.CORBA.Object obj)
  {
    if (obj == null)
      return null;
    else if (obj instanceof hostmock.corba.Host)
      return (hostmock.corba.Host)obj;
    else if (!obj._is_a (id ()))
      throw new org.omg.CORBA.BAD_PARAM ();
    else
    {
      org.omg.CORBA.portable.Delegate delegate = ((org.omg.CORBA.portable.ObjectImpl)obj)._get_delegate ();
      hostmock.corba._HostStub stub = new hostmock.corba._HostStub ();
      stub._set_delegate(delegate);
      return stub;
    }
  }

  public static hostmock.corba.Host unchecked_narrow (org.omg.CORBA.Object obj)
  {
    if (obj == null)
      return null;
    else if (obj instanceof hostmock.corba.Host)
      return (hostmock.corba.Host)obj;
    else
    {
      org.omg.CORBA.portable.Delegate delegate = ((org.omg.CORBA.portable.ObjectImpl)obj)._get_delegate ();
      hostmock.corba._HostStub stub = new hostmock.corba._HostStub ();
      stub._set_delegate(delegate);
      return stub;
    }
  }

}
