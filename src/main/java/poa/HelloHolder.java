package poa;

/**
* poa/HelloHolder.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from idl/Hello.idl
* Sunday, October 8, 2017 5:14:57 PM UTC
*/

public final class HelloHolder implements org.omg.CORBA.portable.Streamable
{
  public poa.Hello value = null;

  public HelloHolder ()
  {
  }

  public HelloHolder (poa.Hello initialValue)
  {
    value = initialValue;
  }

  public void _read (org.omg.CORBA.portable.InputStream i)
  {
    value = poa.HelloHelper.read (i);
  }

  public void _write (org.omg.CORBA.portable.OutputStream o)
  {
    poa.HelloHelper.write (o, value);
  }

  public org.omg.CORBA.TypeCode _type ()
  {
    return poa.HelloHelper.type ();
  }

}
