package hostmock.corba;


/**
* hostmock/corba/HostPOA.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from idl/Host.idl
* Sunday, October 8, 2017 7:09:18 AM UTC
*/

public abstract class HostPOA extends org.omg.PortableServer.Servant
 implements hostmock.corba.HostOperations, org.omg.CORBA.portable.InvokeHandler
{

  // Constructors

  private static java.util.Hashtable _methods = new java.util.Hashtable ();
  static
  {
    _methods.put ("sndAndRcv", new java.lang.Integer (0));
  }

  public org.omg.CORBA.portable.OutputStream _invoke (String $method,
                                org.omg.CORBA.portable.InputStream in,
                                org.omg.CORBA.portable.ResponseHandler $rh)
  {
    org.omg.CORBA.portable.OutputStream out = null;
    java.lang.Integer __method = (java.lang.Integer)_methods.get ($method);
    if (__method == null)
      throw new org.omg.CORBA.BAD_OPERATION (0, org.omg.CORBA.CompletionStatus.COMPLETED_MAYBE);

    switch (__method.intValue ())
    {
       case 0:  // hostmock/corba/Host/sndAndRcv
       {
         String inq = in.read_wstring ();
         org.omg.CORBA.StringHolder ans = new org.omg.CORBA.StringHolder ();
         this.sndAndRcv (inq, ans);
         out = $rh.createReply();
         out.write_wstring (ans.value);
         break;
       }

       default:
         throw new org.omg.CORBA.BAD_OPERATION (0, org.omg.CORBA.CompletionStatus.COMPLETED_MAYBE);
    }

    return out;
  } // _invoke

  // Type-specific CORBA::Object operations
  private static String[] __ids = {
    "IDL:hostmock/corba/Host:1.0"};

  public String[] _all_interfaces (org.omg.PortableServer.POA poa, byte[] objectId)
  {
    return (String[])__ids.clone ();
  }

  public Host _this() 
  {
    return HostHelper.narrow(
    super._this_object());
  }

  public Host _this(org.omg.CORBA.ORB orb) 
  {
    return HostHelper.narrow(
    super._this_object(orb));
  }


} // class HostPOA
