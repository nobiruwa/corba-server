package hostmock.corba;

/**
* hostmock/corba/HostHolder.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from idl/Host.idl
* Sunday, December 10, 2017 12:22:07 PM UTC
*/

public final class HostHolder implements org.omg.CORBA.portable.Streamable
{
  public hostmock.corba.Host value = null;

  public HostHolder ()
  {
  }

  public HostHolder (hostmock.corba.Host initialValue)
  {
    value = initialValue;
  }

  public void _read (org.omg.CORBA.portable.InputStream i)
  {
    value = hostmock.corba.HostHelper.read (i);
  }

  public void _write (org.omg.CORBA.portable.OutputStream o)
  {
    hostmock.corba.HostHelper.write (o, value);
  }

  public org.omg.CORBA.TypeCode _type ()
  {
    return hostmock.corba.HostHelper.type ();
  }

}
