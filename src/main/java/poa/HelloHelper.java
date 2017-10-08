package poa;


/**
* poa/HelloHelper.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from idl/Hello.idl
* Sunday, October 8, 2017 5:14:57 PM UTC
*/

abstract public class HelloHelper
{
  private static String  _id = "IDL:poa/Hello:1.0";

  public static void insert (org.omg.CORBA.Any a, poa.Hello that)
  {
    org.omg.CORBA.portable.OutputStream out = a.create_output_stream ();
    a.type (type ());
    write (out, that);
    a.read_value (out.create_input_stream (), type ());
  }

  public static poa.Hello extract (org.omg.CORBA.Any a)
  {
    return read (a.create_input_stream ());
  }

  private static org.omg.CORBA.TypeCode __typeCode = null;
  synchronized public static org.omg.CORBA.TypeCode type ()
  {
    if (__typeCode == null)
    {
      __typeCode = org.omg.CORBA.ORB.init ().create_interface_tc (poa.HelloHelper.id (), "Hello");
    }
    return __typeCode;
  }

  public static String id ()
  {
    return _id;
  }

  public static poa.Hello read (org.omg.CORBA.portable.InputStream istream)
  {
    return narrow (istream.read_Object (_HelloStub.class));
  }

  public static void write (org.omg.CORBA.portable.OutputStream ostream, poa.Hello value)
  {
    ostream.write_Object ((org.omg.CORBA.Object) value);
  }

  public static poa.Hello narrow (org.omg.CORBA.Object obj)
  {
    if (obj == null)
      return null;
    else if (obj instanceof poa.Hello)
      return (poa.Hello)obj;
    else if (!obj._is_a (id ()))
      throw new org.omg.CORBA.BAD_PARAM ();
    else
    {
      org.omg.CORBA.portable.Delegate delegate = ((org.omg.CORBA.portable.ObjectImpl)obj)._get_delegate ();
      poa._HelloStub stub = new poa._HelloStub ();
      stub._set_delegate(delegate);
      return stub;
    }
  }

  public static poa.Hello unchecked_narrow (org.omg.CORBA.Object obj)
  {
    if (obj == null)
      return null;
    else if (obj instanceof poa.Hello)
      return (poa.Hello)obj;
    else
    {
      org.omg.CORBA.portable.Delegate delegate = ((org.omg.CORBA.portable.ObjectImpl)obj)._get_delegate ();
      poa._HelloStub stub = new poa._HelloStub ();
      stub._set_delegate(delegate);
      return stub;
    }
  }

}
